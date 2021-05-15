package com.example.reni.Slime.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reni.Slime.R;
import com.example.reni.Slime.db.CartDB;
import com.example.reni.Slime.db.SnacksDB;
import com.example.reni.Slime.db.UsersDB;
import com.example.reni.Slime.model.Cart;
import com.example.reni.Slime.model.Snack;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder>{
    Context ctx;
    SnacksDB snacksDB;
    CartDB cartDB;

    public interface onSubTotalChangeListener{
        void onSubTotalChange();
    }

    private onSubTotalChangeListener mListener;

    public CartAdapter(Context ctx, onSubTotalChangeListener mListener){
        this.ctx = ctx;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(ctx).inflate(R.layout.cart_item_view, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final Cart cart = CartDB.currentCart.get(position);

        snacksDB = new SnacksDB(ctx);
        final Snack snack = snacksDB.loadSpecificSnack(cart.snackID);

        String url = snack.coverUrl;
        Picasso.get().load(url).resize(200,200).into(holder.imageRecyclerCartItem, new Callback() {
            @Override
            public void onSuccess() {
                //progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError(Exception e) {

            }
        });

        holder.textRecyclerCartName.setText(snack.name);
        holder.textRecyclerCartPrice.setText("Rp" + snack.price);
        holder.textRecyclerCartQty.setText("" + cart.quantity);

        int qty;
        qty = Integer.parseInt(holder.textRecyclerCartQty.getText().toString());

        if(qty == 1){
            holder.decrementRecyclerCart.setTextColor(holder.itemView.getResources().getColor(R.color.white));
            holder.incrementRecyclerCart.setTextColor(holder.itemView.getResources().getColor(R.color.black));
        }else if(qty == snack.stock){
            holder.decrementRecyclerCart.setTextColor(holder.itemView.getResources().getColor(R.color.black));
            holder.incrementRecyclerCart.setTextColor(holder.itemView.getResources().getColor(R.color.white));
        }else{
            holder.decrementRecyclerCart.setTextColor(holder.itemView.getResources().getColor(R.color.black));
            holder.incrementRecyclerCart.setTextColor(holder.itemView.getResources().getColor(R.color.black));
        }

        holder.buttonDeleteMyCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(ctx)
                        .setMessage("Are you sure want to delete this item from your cart?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(ctx, "Your cart has been updated !", Toast.LENGTH_SHORT).show();

                                cartDB = new CartDB(ctx);
                                cartDB.deleteSpecificCartMyCart(cart.ID);

                                cartDB.loadCart(ctx);
                                cartDB.loadCurrentCart(ctx, UsersDB.activeUser.ID);

                                notifyDataSetChanged();
                                mListener.onSubTotalChange();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });

        holder.incrementRecyclerCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cartDB = new CartDB(ctx);

                int number;
                number = Integer.parseInt(holder.textRecyclerCartQty.getText().toString());
                if(snack.stock==1){
                    holder.decrementRecyclerCart.setTextColor(holder.itemView.getResources().getColor(R.color.white));
                    holder.incrementRecyclerCart.setTextColor(holder.itemView.getResources().getColor(R.color.white));
                }else if(number == snack.stock){
                    holder.decrementRecyclerCart.setTextColor(holder.itemView.getResources().getColor(R.color.black));
                    holder.incrementRecyclerCart.setTextColor(holder.itemView.getResources().getColor(R.color.white));
                }else{
                    if(number == (snack.stock - 1)){
                        holder.decrementRecyclerCart.setTextColor(holder.itemView.getResources().getColor(R.color.black));
                        holder.incrementRecyclerCart.setTextColor(holder.itemView.getResources().getColor(R.color.white));
                    }else{
                        holder.decrementRecyclerCart.setTextColor(holder.itemView.getResources().getColor(R.color.black));
                        holder.incrementRecyclerCart.setTextColor(holder.itemView.getResources().getColor(R.color.black));

                    }
                    number = number + 1;
                    holder.textRecyclerCartQty.setText(number + "");
                    cart.quantity = number;
                }


                for(Cart carts : CartDB.currentCart){
                    if(carts.snackID == cart.snackID){
                        cart.ID = carts.ID;
                        cartDB.updateCartMyCart(ctx, cart);
                        break;
                    }
                }

                cartDB.loadCart(ctx);
                cartDB.loadCurrentCart(ctx, UsersDB.activeUser.ID);

                mListener.onSubTotalChange();
            }
        });


        holder.decrementRecyclerCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cartDB = new CartDB(ctx);

                int number;
                number = Integer.parseInt(holder.textRecyclerCartQty.getText().toString());
                if(snack.stock==1){
                    holder.decrementRecyclerCart.setTextColor(holder.itemView.getResources().getColor(R.color.white));
                    holder.incrementRecyclerCart.setTextColor(holder.itemView.getResources().getColor(R.color.white));
                }else if(number == 1){
                    holder.decrementRecyclerCart.setTextColor(holder.itemView.getResources().getColor(R.color.white));
                    holder.incrementRecyclerCart.setTextColor(holder.itemView.getResources().getColor(R.color.black));
                }else{
                    if(number == 2 && snack.stock > 2){
                        holder.decrementRecyclerCart.setTextColor(holder.itemView.getResources().getColor(R.color.white));
                        holder.incrementRecyclerCart.setTextColor(holder.itemView.getResources().getColor(R.color.black));
                    }else{
                        holder.decrementRecyclerCart.setTextColor(holder.itemView.getResources().getColor(R.color.black));
                        holder.incrementRecyclerCart.setTextColor(holder.itemView.getResources().getColor(R.color.black));

                    }
                    number = number - 1;
                    holder.textRecyclerCartQty.setText(number + "");
                    cart.quantity = number;
                }


                for(Cart carts : CartDB.currentCart){
                    if(carts.snackID == cart.snackID){
                        cart.ID = carts.ID;
                        cartDB.updateCartMyCart(ctx, cart);
                        break;
                    }
                }

                cartDB.loadCart(ctx);
                cartDB.loadCurrentCart(ctx, UsersDB.activeUser.ID);

                mListener.onSubTotalChange();
            }
        });
    }

    @Override
    public int getItemCount() {
        return CartDB.currentCart.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView imageRecyclerCartItem, buttonDeleteMyCart;
        TextView textRecyclerCartName, textRecyclerCartPrice, textRecyclerCartQty;
        Button decrementRecyclerCart, incrementRecyclerCart;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);

            imageRecyclerCartItem = itemView.findViewById(R.id.imageRecyclerCartItem);
            textRecyclerCartName = itemView.findViewById(R.id.textRecyclerCartName);
            textRecyclerCartPrice = itemView.findViewById(R.id.textRecyclerCartPrice);
            textRecyclerCartQty = itemView.findViewById(R.id.textRecyclerCartQty);
            decrementRecyclerCart = itemView.findViewById(R.id.decrementRecyclerCart);
            incrementRecyclerCart = itemView.findViewById(R.id.incrementRecyclerCart);
            buttonDeleteMyCart = itemView.findViewById(R.id.buttonDeleteMyCart);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
