package me.egorand.introtorxjava.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import me.egorand.introtorxjava.data.Repo;

/**
 * @author Egor
 */
public class ReposAdapter extends RecyclerView.Adapter<ReposAdapter.ViewHolder> {

    private final LayoutInflater layoutInflater;

    private final List<Repo> repos;

    public ReposAdapter(LayoutInflater layoutInflater) {
        this.layoutInflater = layoutInflater;
        this.repos = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(layoutInflater.inflate(android.R.layout.simple_list_item_2, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Repo repo = repos.get(position);
        holder.nameView.setText(repo.name);
        holder.descriptionView.setText(repo.description);
    }

    @Override
    public int getItemCount() {
        return repos.size();
    }

    public void setRepos(List<Repo> repos) {
        int oldSize = this.repos.size();
        this.repos.clear();
        notifyItemRangeRemoved(0, oldSize);
        this.repos.addAll(repos);
        notifyItemRangeInserted(0, repos.size());
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView nameView;
        TextView descriptionView;

        public ViewHolder(View itemView) {
            super(itemView);
            nameView = (TextView) itemView.findViewById(android.R.id.text1);
            descriptionView = (TextView) itemView.findViewById(android.R.id.text2);
        }
    }
}
