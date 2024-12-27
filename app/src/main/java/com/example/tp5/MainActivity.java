package com.example.tp5;


import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private EditText etProductName, etProductQuantity;
    private TextView tvProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this);

        etProductName = findViewById(R.id.etProductName);
        etProductQuantity = findViewById(R.id.etProductQuantity);
        tvProducts = findViewById(R.id.tvProducts);

        Button btnAddProduct = findViewById(R.id.btnAddProduct);
        Button btnViewProducts = findViewById(R.id.btnViewProducts);
        Button btnDeleteProduct = findViewById(R.id.btnDeleteProduct);

        // Ajouter un produit
        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etProductName.getText().toString();
                int quantity = Integer.parseInt(etProductQuantity.getText().toString());
                dbHelper.insertProduct(name, quantity);
                Toast.makeText(MainActivity.this, "Produit ajouté", Toast.LENGTH_SHORT).show();
            }
        });

        // Afficher tous les produits
        btnViewProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor = dbHelper.getAllProducts();
                StringBuilder products = new StringBuilder();

                if (cursor.moveToFirst()) {
                    do {
                        products.append("ID: ").append(cursor.getInt(0))
                                .append(",      ")
                                .append(" Nom: ").append(cursor.getString(1))
                                .append(",      ")
                                .append(" Quantité: ").append(cursor.getInt(2))
                                .append("\n");
                    } while (cursor.moveToNext());
                }
                cursor.close();
                tvProducts.setText(products.toString());
            }
        });

        // Supprimer un produit
        findViewById(R.id.btnDeleteProduct).setOnClickListener(v -> {
            String name = etProductName.getText().toString();
            boolean result = dbHelper.deleteProduct(name);
            if (result) {
                etProductName.setText("");
                etProductQuantity.setText("");
                Toast.makeText(this, "Produit supprimé", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Erreur lors de la suppression", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
