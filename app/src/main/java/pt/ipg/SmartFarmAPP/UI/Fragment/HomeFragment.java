package pt.ipg.SmartFarmAPP.UI.Fragment;

import android.app.AlertDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

import pt.ipg.SmartFarmAPP.Entity.Node;
import pt.ipg.SmartFarmAPP.MainActivity;
import pt.ipg.SmartFarmAPP.R;
import pt.ipg.SmartFarmAPP.SyncJobIntent;
import pt.ipg.SmartFarmAPP.UI.Fragment.Adapter.NodeAdapter;
import pt.ipg.SmartFarmAPP.UI.Fragment.Dialog.AddNodeDialog;
import pt.ipg.SmartFarmAPP.ViewModel.NodeViewModel;


/**
 * Created by Daey
 */
import static android.content.ContentValues.TAG;
import static android.view.View.GONE;
public class HomeFragment extends Fragment implements AddNodeDialog.OnInputSelected{

    private ProgressBar progressbar;
    Node nodeAt;

    public static HomeFragment newInstance(){
        HomeFragment homeFragment = new  HomeFragment();
        Bundle args = new Bundle();
        homeFragment.setArguments(args);
        return homeFragment;
    }

    @Override // input da dialog
    public void sendInput(String input) {
        Log.d(TAG, "sendInput: " + input);

        // Add Local
        NodeViewModel nodeViewModel = ViewModelProviders.of(getActivity()).get(NodeViewModel.class);

        Node newNode = new Node("Daniel@ept.pt","Lora ESP32 (Android)","0.1","1A0000022", (float) -7.24545154, 40.25414541f,900,1,"192.168.000.000");
        nodeViewModel.insert(newNode);
        Toast.makeText(getActivity(), "Node inserido LOCALMENTE", Toast.LENGTH_SHORT).show();
        // Job Intent <--- sync DATABASE com WebAPI


        //Intent syncDB = new Intent(getContext(), SyncJobIntent.class);
        //SyncJobIntent.enqueueWork(getContext(),syncDB);
        startService("Sync Oracle - POST",-1);

    }

    public void startService(String input, int nodeID) {
        Intent serviceIntent = new Intent(getContext(), SyncJobIntent.class);
        serviceIntent.putExtra("inputExtra", input);
        serviceIntent.putExtra("inputnodeID", nodeID);

       SyncJobIntent.enqueueWork(getContext(), serviceIntent);








    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, null);
        // mais nodes
        FloatingActionButton fab = view.findViewById(R.id.fabAddNode);//Find fab Id
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // faz cenas
                //Toast.makeText(getActivity(), "Montes de cenas!", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onClick: opening dialog");

                AddNodeDialog dialog = new AddNodeDialog();
                dialog.setTargetFragment(HomeFragment.this, 11);
                dialog.show(getFragmentManager(), "AddNodeDialog");
            }
        });

        // Content in view
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        final NodeAdapter nodeAdapter = new NodeAdapter();
        recyclerView.setAdapter(nodeAdapter);

        ProgressBar progressbar = (ProgressBar) view.findViewById(R.id.progressbar);

        final NodeViewModel nodeViewModel = ViewModelProviders.of(getActivity()).get(NodeViewModel.class);
        nodeViewModel.getAllNodes().observe(getActivity(), new Observer<List<Node>>() {
            @Override
            public void onChanged(@Nullable List<Node> nodes) {
                nodeAdapter.setNodes(nodes);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                nodeAt = nodeAdapter.getNodeAt(viewHolder.getAdapterPosition());

                AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                dialog.setCancelable(false);
                dialog.setTitle("CONFIRMAÇÃO");
                dialog.setMessage("Tem a certeza que pretende remover este Node? e todos os seus sensores!" );
                dialog.setPositiveButton("Remover", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        //Action for "Delete".
                        startService("Sync Oracle - DELETE",nodeAt.getId());
                        nodeViewModel.delete(nodeAt); ///  <--- sem nenhuma confirmação!!! todo confirmação delete node

                        Snackbar snackbar = Snackbar.make(getView(), "Node removido.", Snackbar.LENGTH_LONG);
                        nodeAdapter.notifyDataSetChanged();
                        snackbar.show();

                    }
                })
                        .setNegativeButton("Cancelar ", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Snackbar snackbar = Snackbar.make(getView(), "Acção cancelada.", Snackbar.LENGTH_LONG);
                                nodeAdapter.notifyDataSetChanged();
                                snackbar.show();
                            }
                        });

                final AlertDialog alert = dialog.create();
                alert.show();



                 // Toast.makeText(getContext(), "Node deleted", Toast.LENGTH_SHORT).show();
                // SnakBAR para mostrarf essas cenas
                /*
                Snackbar snackbar = Snackbar
                        .make(getView(), "Pretende remover o Node #"+nodeAt.getId()+"?", Snackbar.LENGTH_LONG)
                        .setAction("SIM", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                startService("Sync Oracle - DELETE",nodeAt.getId());
                                nodeViewModel.delete(nodeAt); ///  <--- sem nenhuma confirmação!!! todo confirmação delete node

                                Snackbar snackbar1 = Snackbar.make(view, "Node removido!", Snackbar.LENGTH_SHORT);
                                nodeAdapter.notifyDataSetChanged();
                                snackbar1.show();
                            }

                        });

                snackbar.show();
                // DELETE em ORACLE
                nodeAdapter.notifyDataSetChanged();
*/
            }
        }).attachToRecyclerView(recyclerView);
       // FIM --- Content in view

        progressbar.setVisibility(GONE);

        return view;
    }

}
