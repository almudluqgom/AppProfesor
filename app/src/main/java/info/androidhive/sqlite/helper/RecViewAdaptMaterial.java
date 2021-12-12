
package info.androidhive.sqlite.helper;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appprofesor.R;

import java.util.List;

import info.androidhive.sqlite.model.Material;

public class RecViewAdaptMaterial extends RecyclerView.Adapter<RecViewAdaptMaterial.ViewHolder> implements View.OnClickListener{

    public List<Material> MaterialLista;
    private View.OnClickListener listener;

    public List<Material> getMaterialLista() {
        return MaterialLista;
    }
    public void setMaterialLista(List<Material> materialesLista) {
        MaterialLista = materialesLista;
    }

    @Override
    public void onClick(View v) {
        if(listener!=null) {
            listener.onClick(v);
        }
    }
    public RecViewAdaptMaterial(List<Material> matLista) {
        Log.d("ListAdapter", "");
        this.MaterialLista = matLista;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("onCreateView", "");
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_profe,parent,false);
        view.setOnClickListener(this);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("onBindView", "");
        Material mat=MaterialLista.get(position);
        holder.nombreMat.setText(mat.getNombre());
        holder.cantObj.setText(mat.getCantidad());
        holder.fotoMat.setImageResource(mat.getIdFoto());

    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        Log.d("onAttached", "");
        super.onAttachedToRecyclerView(recyclerView);
    }
    @Override
    public int getItemCount() {
        Log.d("getItemCount", "");
        return MaterialLista.size();     }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    //-----------------------------------------------------------------------------------------
    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView nombreMat,cantObj;
        ImageView fotoMat;
        CardView cardmaterial;
        //private final Context context;

        public ViewHolder(View itemView){
            super(itemView);
            Log.d("PostViewHolder", "");
            //context = itemView.getContext();
            nombreMat =(TextView)itemView.findViewById(R.id.nombreObj);
            cantObj =(TextView)itemView.findViewById(R.id.CantidadObj);
            fotoMat=(ImageView)itemView.findViewById(R.id.imgObjeto);
            cardmaterial =itemView.findViewById(R.id.carta);
        }
    }
//-----------------------------------------------------------------------------------------
}
