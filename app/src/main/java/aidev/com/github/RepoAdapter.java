package aidev.com.github;


import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class RepoAdapter extends RecyclerView.Adapter<RepoAdapter.ViewHolder>  {


    private List<RepoInitialiser> listitem;
    Context ctx;


    public RepoAdapter(Context ctx, List<RepoInitialiser> listitem) {
        this.listitem = listitem;
        this.ctx = ctx;
    }

    @Override
    public RepoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.repo_item,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final RepoInitialiser homeInitialiser = listitem.get(position);


        String link = homeInitialiser.getLink();

        holder.links.setText(""+link);

        holder.links.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = ""+homeInitialiser.getHtmlUrl()+"/"+homeInitialiser.getLink();
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.setPackage("com.android.chrome");
                try {
                    ctx.startActivity(i);
                } catch (ActivityNotFoundException e) {

                    i.setPackage(null);
                    ctx.startActivity(i);
                }
            }
        });

        holder.created.setText(homeInitialiser.getCreatedOn());
        holder.update.setText(homeInitialiser.getUpadatedOn());
        holder.push.setText(homeInitialiser.getPushedOn());
        if (homeInitialiser.getDescription().equals("null")) {
            holder.desc.setText("Not available");
        } else {
            holder.desc.setText(homeInitialiser.getDescription());
        }


    }

    @Override
    public int getItemCount() {
        return listitem.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView links;
        public TextView desc, push, update, created;


        public ViewHolder(View itemView) {
            super(itemView);

            links = (TextView) itemView.findViewById(R.id.reponame);
            desc = (TextView) itemView.findViewById(R.id.description);
            push = (TextView) itemView.findViewById(R.id.pushedon);
            update = (TextView) itemView.findViewById(R.id.updatedon);
            created = (TextView) itemView.findViewById(R.id.createdon);

        }
    }

    public void updateList(List<RepoInitialiser> listitem){
        this.listitem = listitem;
        notifyDataSetChanged();
    }
}
