package com.example.evan_mulcare_assignment_two.Controllers;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.evan_mulcare_assignment_two.Models.Booking;
import com.example.evan_mulcare_assignment_two.Models.CurrentUserSingleton;
import com.example.evan_mulcare_assignment_two.Models.DatabaseHandlerSingleton;
import com.example.evan_mulcare_assignment_two.R;
import com.example.evan_mulcare_assignment_two.Views.ScheduleNewBookingActivity;

import java.util.List;

public class BookingVerticalAdapter extends RecyclerView.Adapter<BookingVerticalAdapter.ViewHolder> {
    private List<Booking> itemList;
    private Context context;
    public BookingVerticalAdapter(List<Booking> itemList,Context context) {
        this.itemList = itemList;
        this.context = context;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vertical_booking_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Booking currentBookingItem = itemList.get(position);
        setBookingDetails(holder, currentBookingItem);
        setBookingStatus(holder, currentBookingItem);

    }
    private void setBookingDetails(ViewHolder holder, Booking booking) {
        holder.timeSlotText.setText(booking.getBookingTime());
        holder.headingText.setText(booking.getTitle());
        holder.imageView.setImageResource(booking.getImageResourceId());
        holder.dateText.setText(booking.getBookingDate());
    }

    private void setBookingStatus(ViewHolder holder, Booking booking) {
        if (booking.isBooked()) {
            setStatusForBookedItem(holder, booking);
        } else {
            setStatusForAvailableItem(holder);
        }
    }

    private void setStatusForBookedItem(ViewHolder holder, Booking booking) {
        if (booking.getUserName().equals(CurrentUserSingleton.getInstance().getCurrentUser().getUsername())) {
            // Booked by the current user
            setBookingStatusTextAndColor(holder,  context.getResources().getString(R.string.booked_by_you), "#FFA500");
        } else {
            // Booked by another user
            String bookedByMessage = context.getResources().getString(R.string.booked_by_user, booking.getUserName());
            setBookingStatusTextAndColor(holder, bookedByMessage, "#c62923");
        }
    }

    private void setStatusForAvailableItem(ViewHolder holder) {
        // Available
        setBookingStatusTextAndColor(holder, context.getResources().getString(R.string.booking_status_available), "#22C55E");
    }

    private void setBookingStatusTextAndColor(ViewHolder holder, String statusText, String colorCode) {
        int statusColor = Color.parseColor(colorCode);
        holder.statusText.setText(statusText);
        holder.statusText.setTextColor(statusColor);
        holder.statusIcon.setImageTintList(ColorStateList.valueOf(statusColor));
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        ImageView statusIcon;
        TextView timeSlotText;
        TextView headingText;
        TextView statusText;

        TextView dateText;
        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            timeSlotText = itemView.findViewById(R.id.timeSlotText);
            headingText = itemView.findViewById(R.id.headingText);
            statusText = itemView.findViewById(R.id.statusText);
            statusIcon = itemView.findViewById(R.id.statusIcon);
            dateText =  itemView.findViewById(R.id.dateText);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                Booking clickedBooking = itemList.get(position);

                if (!clickedBooking.isBooked()) {
                    handleUnbookedItemClick(view.getContext(), clickedBooking);
                } else {
                    handleBookedItemClick(view.getContext(), clickedBooking);
                }
            }
        }
        private void handleUnbookedItemClick(Context context, Booking clickedBooking) {
            if (updateBookingUser(clickedBooking)) {
                startScheduleNewBookingActivity(context, clickedBooking);
                Toast.makeText(context, R.string.booking_confirmed_message, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, R.string.error_updating_booking, Toast.LENGTH_SHORT).show();
            }
        }

        private void handleBookedItemClick(Context context, Booking clickedBooking) {
            if (isCurrentUserOwner(clickedBooking)) {
                startScheduleNewBookingActivity(context, clickedBooking);
            } else {
                showBookingAlreadyTakenToast(context, clickedBooking);
            }
        }

        private boolean updateBookingUser(Booking clickedBooking) {
            return  DatabaseHandlerSingleton.getInstance(context).updateBookingUser(clickedBooking, CurrentUserSingleton.getInstance().getCurrentUser().getUsername());
        }

        private void startScheduleNewBookingActivity(Context context, Booking clickedBooking) {
            Intent intent = new Intent(context, ScheduleNewBookingActivity.class);
            intent.putExtra( context.getString(R.string.extra_booking_title), clickedBooking.getTitle());
            intent.putExtra( context.getString(R.string.extra_booking_time), clickedBooking.getBookingTime());
            intent.putExtra( context.getString(R.string.extra_booking_date), clickedBooking.getBookingDate());
            context.startActivity(intent);
        }

        private boolean isCurrentUserOwner(Booking clickedBooking) {
            return clickedBooking.getUserName().equals(CurrentUserSingleton.getInstance().getCurrentUser().getUsername());
        }

        private void showBookingAlreadyTakenToast(Context context, Booking clickedBooking) {
            String errorMessage = context.getString(R.string.booking_already_taken, clickedBooking.getUserName());
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
        }

    }
}
