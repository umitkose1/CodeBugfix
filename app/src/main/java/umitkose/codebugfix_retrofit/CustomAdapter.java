package umitkose.codebugfix_retrofit;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;

import umitkose.codebugfix_retrofit.Model.User;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> implements Filterable {

    private ArrayList<User> userList;
    private ArrayList<User> filteredUserList;
    private Context context;
    private CustomItemClickListener customItemClickListener;

    public CustomAdapter(Context context,ArrayList<User> userArrayList,CustomItemClickListener customItemClickListener) {
        this.context = context;
        this.userList = userArrayList;
        this.filteredUserList = userArrayList;
        this.customItemClickListener = customItemClickListener;
    }

    @Override
    public CustomAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, viewGroup, false);
        final MyViewHolder myViewHolder = new MyViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // for click item listener
                customItemClickListener.onItemClick(filteredUserList.get(myViewHolder.getAdapterPosition()),myViewHolder.getAdapterPosition());
            }
        });
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(CustomAdapter.MyViewHolder viewHolder, int position) {

        viewHolder.userId.setText(filteredUserList.get(position).getUserId());
        viewHolder.id.setText(filteredUserList.get(position).getId());
        viewHolder.userTitle.setText(filteredUserList.get(position).getTitle());
        viewHolder.userBody.setText(filteredUserList.get(position).getBody());
    }

    @Override
    public int getItemCount() {
        return filteredUserList.size();
    }

    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String searchString = charSequence.toString();

                if (searchString.isEmpty()) {

                    filteredUserList = userList;

                } else {

                    ArrayList<User> tempFilteredList = new ArrayList<>();

                    for (User user : userList) {

                        // search for user name
                        if (user.getTitle().toLowerCase().contains(searchString)) {

                            tempFilteredList.add(user);
                        }
                    }

                    filteredUserList = tempFilteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredUserList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredUserList = (ArrayList<User>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView userId;
        private TextView id;
        private TextView userBody;
        private TextView userTitle;
        public MyViewHolder(View view) {
            super(view);
            userId = (TextView)view.findViewById(R.id.userId);
            id = (TextView)view.findViewById(R.id.id);
            userTitle = (TextView)view.findViewById(R.id.userTitle);
            userBody = (TextView)view.findViewById(R.id.userBody);

        }
    }
}
