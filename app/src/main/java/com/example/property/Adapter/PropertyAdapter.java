package com.example.property.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.property.R;
import com.example.property.UpdateProperty;
import com.example.property.models.Plots;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class PropertyAdapter extends FirebaseRecyclerAdapter<Plots, PropertyAdapter.PropertyViewHolder> {

    Context context;

    public PropertyAdapter(@NonNull FirebaseRecyclerOptions<Plots> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull final PropertyViewHolder holder, int position, @NonNull final Plots model) {

        final String key = getRef(position).getKey();

        String prprty_type_id = "";
        final String constructed = model.getConstructed();

        if (model.getProperty_type_id().equals("1")) {
            prprty_type_id = "Residential";
        }
        if (model.getProperty_type_id().equals("2")) {
            prprty_type_id = "Commercial";
        }

        holder.propertyType.setText(prprty_type_id);
        holder.precinct.setText(model.getPrecinct_id());
        holder.road.setText(model.getRoad_id());
        holder.plotname.setText(model.getName());
        holder.plotno.setText(model.getplot_no());
        holder.is_constructed.setText(constructed);
        holder.rooms.setText(model.getRooms());
        holder.stories.setText(model.getStories());
        holder.square_yard.setText(model.getSq_yrds());
        holder.pricerangeFrom.setText(model.getPlot_price_range_from());
        holder.pricerangeTo.setText(model.getPlot_price_range_to());
        holder.addedBy.setText("Company Id : " + model.getCompany_id() +
                "\nAgent name : " + model.getAgent_name() + "\nAgent id : " + model.getAgent_id());

        holder.editProperty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, UpdateProperty.class);
                intent.putExtra("plotname",model.getName());
                intent.putExtra("constructed",constructed);
                intent.putExtra("rooms",model.getRooms());
                intent.putExtra("stories",model.getStories());
                intent.putExtra("pricerangeFrom",model.getPlot_price_range_from());
                intent.putExtra("pricerangeTo",model.getPlot_price_range_to());
                intent.putExtra("key",key);
                context.startActivity(intent);

            }
        });

        holder.see_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.title_precinct.setVisibility(View.VISIBLE);
                holder.precinct.setVisibility(View.VISIBLE);
                holder.title_property_type.setVisibility(View.VISIBLE);
                holder.propertyType.setVisibility(View.VISIBLE);
                holder.title_square_yard.setVisibility(View.VISIBLE);
                holder.square_yard.setVisibility(View.VISIBLE);
                holder.title_road.setVisibility(View.VISIBLE);
                holder.road.setVisibility(View.VISIBLE);
                holder.title_pricerange.setVisibility(View.VISIBLE);
                holder.pricerangeTo.setVisibility(View.VISIBLE);
                holder.pricerangeFrom.setVisibility(View.VISIBLE);
                holder.title_addedBy.setVisibility(View.VISIBLE);
                holder.addedBy.setVisibility(View.VISIBLE);
                holder.editProperty.setVisibility(View.VISIBLE);
                if (constructed.equals("Yes")) {
                    holder.tv_rooms.setVisibility(View.VISIBLE);
                    holder.stories.setVisibility(View.VISIBLE);
                    holder.tv_stories.setVisibility(View.VISIBLE);
                    holder.rooms.setVisibility(View.VISIBLE);

                }

                holder.see_more.setVisibility(View.GONE);
                holder.see_less.setVisibility(View.VISIBLE);
            }
        });

        holder.see_less.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.title_precinct.setVisibility(View.GONE);
                holder.precinct.setVisibility(View.GONE);
                holder.title_property_type.setVisibility(View.GONE);
                holder.propertyType.setVisibility(View.GONE);
                holder.title_square_yard.setVisibility(View.GONE);
                holder.square_yard.setVisibility(View.GONE);
                holder.title_road.setVisibility(View.GONE);
                holder.road.setVisibility(View.GONE);
                holder.title_pricerange.setVisibility(View.GONE);
                holder.pricerangeTo.setVisibility(View.GONE);
                holder.pricerangeFrom.setVisibility(View.GONE);
                holder.title_addedBy.setVisibility(View.GONE);
                holder.addedBy.setVisibility(View.GONE);

                holder.tv_rooms.setVisibility(View.GONE);
                holder.stories.setVisibility(View.GONE);
                holder.tv_stories.setVisibility(View.GONE);
                holder.rooms.setVisibility(View.GONE);

                holder.editProperty.setVisibility(View.GONE);
                holder.see_more.setVisibility(View.VISIBLE);
                holder.see_less.setVisibility(View.GONE);
            }
        });

        holder.mShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/bold");
                sharingIntent.putExtra(Intent.EXTRA_TEXT, "Plot Name: " + model.getName()
                        + "\nPlot No: " + model.getplot_no()
                        + "\nRoad: " + model.getRoad_id()
                        + "\nSq/yrd: " + model.getSq_yrds()
                        + "\nPrice: Rs." + model.getPlot_price_range_to() + " to Rs." + model.getPlot_price_range_from());
                context.startActivity(Intent.createChooser(sharingIntent, "Share via"));
            }
        });


    }

    @NonNull
    @Override
    public PropertyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.itemview, parent, false);

        return new PropertyViewHolder(view);
    }

    class PropertyViewHolder extends RecyclerView.ViewHolder {

        TextView propertyType, precinct, road, plotname, plotno,
                square_yard, pricerangeFrom, pricerangeTo, addedBy, is_constructed, stories, rooms;

        TextView title_property_type, title_precinct, title_road,
                see_more, title_square_yard, title_pricerange, title_addedBy, see_less, tv_stories, tv_rooms;

        Button mShare;

        ImageButton editProperty;

        public PropertyViewHolder(@NonNull View itemView) {
            super(itemView);

            propertyType = itemView.findViewById(R.id.tv_property_type);
            precinct = itemView.findViewById(R.id.tv_precinct);
            road = itemView.findViewById(R.id.tv_road);
            plotname = itemView.findViewById(R.id.tv_plot_name);
            plotno = itemView.findViewById(R.id.tv_plot_no);
            square_yard = itemView.findViewById(R.id.tv_square_yard);
            pricerangeFrom = itemView.findViewById(R.id.tv_price_range_from);
            pricerangeTo = itemView.findViewById(R.id.tv_price_range_to);
            addedBy = itemView.findViewById(R.id.tv_addedBy);

            title_property_type = itemView.findViewById(R.id.tv_text5);
            title_precinct = itemView.findViewById(R.id.tv_text6);
            title_road = itemView.findViewById(R.id.tv_text10);
            title_square_yard = itemView.findViewById(R.id.tv_text9);
            title_pricerange = itemView.findViewById(R.id.tv_text4);
            title_addedBy = itemView.findViewById(R.id.tv_text11);

            is_constructed = itemView.findViewById(R.id.is_constructed);
            stories = itemView.findViewById(R.id.stories);
            rooms = itemView.findViewById(R.id.rooms);
            tv_rooms = itemView.findViewById(R.id.tv_rooms);
            tv_stories = itemView.findViewById(R.id.tv_stories);
            see_more = itemView.findViewById(R.id.seemore_btn);
            see_less = itemView.findViewById(R.id.seeless_btn);

            editProperty = itemView.findViewById(R.id.edit_property);
            mShare = itemView.findViewById(R.id.sharebtn);

        }
    }

}
