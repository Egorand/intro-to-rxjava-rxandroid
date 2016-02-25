/*
 * Copyright 2016 Egor Andreevici
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.egorand.introtorxjava.ui.adapters;

import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import me.egorand.introtorxjava.data.entities.Repo;

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
        holder.nameView.setText(repo.getName());
        holder.descriptionView.setText(repo.getDescription());
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
            nameView.setTypeface(Typeface.DEFAULT_BOLD);
            descriptionView = (TextView) itemView.findViewById(android.R.id.text2);
        }
    }
}
