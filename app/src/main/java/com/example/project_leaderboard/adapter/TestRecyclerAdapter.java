package com.example.project_leaderboard.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_leaderboard.R;
import com.example.project_leaderboard.db.entity.League;
import com.example.project_leaderboard.db.util.RecyclerViewItemClickListener;

import java.util.List;
import java.util.Objects;

    public class TestRecyclerAdapter extends RecyclerView.Adapter<TestRecyclerAdapter.ViewHolder> {

        private List<League> data;
        private RecyclerViewItemClickListener listener;

        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder
        static class ViewHolder extends RecyclerView.ViewHolder {
            // each data item is just a string in this case
            TextView textView;
            ViewHolder(TextView textView) {
                super(textView);
                this.textView = textView;
            }
        }

        public TestRecyclerAdapter(RecyclerViewItemClickListener listener) {
            this.listener = listener;
        }

        @Override
        public TestRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            TextView v = (TextView) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recycler_view, parent, false);
            final ViewHolder viewHolder = new ViewHolder(v);
            v.setOnClickListener(view -> listener.onItemClick(view, viewHolder.getAdapterPosition()));
            v.setOnLongClickListener(view -> {
                listener.onItemLongClick(view, viewHolder.getAdapterPosition());
                return true;
            });
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(TestRecyclerAdapter.ViewHolder holder, int position) {
            League item = data.get(position);
            holder.textView.setText(item.toString());
        }

        @Override
        public int getItemCount() {
            if (data != null) {
                return data.size();
            } else {
                return 0;
            }
        }

        public void setData(final List<League> data) {
            if (this.data == null) {
                this.data = data;
                notifyItemRangeInserted(0, data.size());
            } else {
                DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                    @Override
                    public int getOldListSize() {
                        return TestRecyclerAdapter.this.data.size();
                    }

                    @Override
                    public int getNewListSize() {
                        return data.size();
                    }

                    @Override
                    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {

                        if (TestRecyclerAdapter.this.data instanceof League) {
                            return (TestRecyclerAdapter.this.data.get(oldItemPosition)).getLeagueId().equals(
                                    (data.get(newItemPosition)).getLeagueId());
                        }
                        return false;
                    }

                    @Override
                    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                        if (TestRecyclerAdapter.this.data instanceof League) {
                            League newClient = data.get(newItemPosition);
                            League oldClient = TestRecyclerAdapter.this.data.get(newItemPosition);
                            /*
                            return Objects.equals(newClient.getLeagueId(), oldClient.getLeagueId())
                                    && Objects.equals(newClient.getFirstName(), oldClient.getFirstName())
                                    && Objects.equals(newClient.getLastName(), oldClient.getLastName());

                             */
                        }
                        return false;
                    }
                });
                this.data = data;
                result.dispatchUpdatesTo(this);
            }
        }
    }

