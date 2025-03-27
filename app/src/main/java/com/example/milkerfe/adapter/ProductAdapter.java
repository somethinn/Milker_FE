package com.example.milkerfe.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.milkerfe.R;
import com.example.milkerfe.model.Product;
import java.util.List;

public class ProductAdapter extends ArrayAdapter<Product> {
    private Context context;
    private List<Product> products;

    public ProductAdapter(Context context, List<Product> products) {
        super(context, 0, products);
        this.context = context;
        this.products = products;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.product_item, parent, false);
        }

        Product product = products.get(position);

        ImageView ivProductImage = convertView.findViewById(R.id.iv_product_image);
        TextView tvProductName = convertView.findViewById(R.id.tv_product_name);
        TextView tvPrice = convertView.findViewById(R.id.tv_price);
        TextView tvQuantity = convertView.findViewById(R.id.tv_quantity);
        EditText etOrderQuantity = convertView.findViewById(R.id.et_order_quantity);

        // Load the image using Glide
        Glide.with(context)
                .load("https://www.heritagefoods.in/blog/wp-content/uploads/2020/12/shutterstock_539045662.jpg")
                .placeholder(R.drawable.ic_launcher_background) // Optional placeholder
                .error(R.drawable.ic_launcher_foreground) // Optional error image
                .into(ivProductImage);

        tvProductName.setText(product.getProductName());
        tvPrice.setText("Price: $" + String.format("%.1f", product.getPrice()));
        tvQuantity.setText("Quantity: " + product.getQuantity());
        etOrderQuantity.setTag(product);

        return convertView;
    }
}