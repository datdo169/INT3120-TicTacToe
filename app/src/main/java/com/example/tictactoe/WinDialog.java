package com.example.tictactoe;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class WinDialog extends Dialog {
    private final String message;
    private final OnlineMode onlineMode;
    public WinDialog(@NonNull Context context, String message ) {
        super(context);
        this.message = message;
        this.onlineMode = ((OnlineMode) context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.win_dialog_layout);

        final TextView messageTV = findViewById(R.id.messageTV);
        final Button startNewBtn = findViewById(R.id.startNewBtn);

        messageTV.setText(message);

        startNewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dismiss();
                getContext().startActivity(new Intent(getContext(), PlayerName.class));
                onlineMode.finish();
            }
        });
    }
}
