package com.example.tictactoe;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ComputerMode extends AppCompatActivity {

    private LinearLayout player1Layout, player2Layout;
    private ImageView image1, image2, image3, image4, image5, image6, image7, image8, image9;
    private TextView player1TV, player2TV;
    // winning combination
    private final List<int[]> combinationList = new ArrayList<>();
    private final List<Integer> doneBoxes = new ArrayList<>();
    private List<String> boxesSelectedTmp;
    private final ArrayList<String> boxesSelectedBy = new ArrayList<>();//done boxes position by users so users won't select the box again
    private final List<ImageView> board = new ArrayList<>();
    // player unique Id
    private String playerUniqueId = "0";
    // opponent unique ID
    private final String computerId = "0";
    private int depth = 0;


    private String playerTurn = "";

    //selected by player empty fields will be replaced by player_id
//    private final String[] boxesSelectedBy = {"", "", "","", "", "","", "", ""};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        player1Layout = findViewById(R.id.player1Layout);
        player2Layout = findViewById(R.id.player2Layout);

        image1 = findViewById(R.id.image1);
        image2 = findViewById(R.id.image2);
        image3 = findViewById(R.id.image3);
        image4 = findViewById(R.id.image4);
        image5 = findViewById(R.id.image5);
        image6 = findViewById(R.id.image6);
        image7 = findViewById(R.id.image7);
        image8 = findViewById(R.id.image8);
        image9 = findViewById(R.id.image9);

        for (int i = 0; i < 9; i++) {
            boxesSelectedBy.add("");
        }

        player1TV = findViewById(R.id.player1TV);
        player2TV = findViewById(R.id.player2TV);

        // getting player name from PlayerName.class
        final String getPlayerName = getIntent().getStringExtra("playerName");

        combinationList.add(new int[]{0, 1, 2});
        combinationList.add(new int[]{3, 4, 5});
        combinationList.add(new int[]{6, 7, 8});
        combinationList.add(new int[]{0, 3, 6});
        combinationList.add(new int[]{1, 4, 7});
        combinationList.add(new int[]{2, 5, 8});
        combinationList.add(new int[]{2, 4, 6});
        combinationList.add(new int[]{0, 4, 8});

        //generate player unique id
        playerUniqueId = String.valueOf(System.currentTimeMillis());
        playerTurn = playerUniqueId;
        // setting player name to TextView
        player1TV.setText(getPlayerName);

        player2TV.setText("Computer");


        image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!doneBoxes.contains(1)) {
                    play(v, 1);
                }
            }
        });
        image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!doneBoxes.contains(2)) {
                    play(v, 2);
                }
            }
        });

        image3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!doneBoxes.contains(3)) {
                    play(v, 3);
                }
            }
        });

        image4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!doneBoxes.contains(4)) {
                    play(v, 4);
                }
            }
        });

        image5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!doneBoxes.contains(5)) {
                    play(v, 5);
                }
            }
        });

        image6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!doneBoxes.contains(6)) {
                    play(v, 6);
                }
            }
        });

        image7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!doneBoxes.contains(7)) {
                    play(v, 7);
                }
            }
        });

        image8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!doneBoxes.contains(8)) {
                    play(v, 8);
                }
            }
        });

        image9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!doneBoxes.contains(9)) {
                    play(v, 9);
                }
            }
        });


    }

    private void play(View v, int boxPosition) {
        if (!doneBoxes.contains(boxPosition) && playerTurn.equals(playerUniqueId)) {
            selectBox(((ImageView) v), boxPosition, playerTurn);

            Handler handler = new Handler();
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    boxesSelectedTmp = boxesSelectedBy;
                    ArrayList<Mark> arrMark = solver("imgO");
                    if (arrMark.size() > 0) {
                        int max = arrMark.get(0).getPoint();
                        int index = 0;
                        for (int i = 0; i < arrMark.size(); i++) {
                            if (max < arrMark.get(i).getPoint()) {
                                max = arrMark.get(i).getPoint();
                                index = i;
                            }
                        }

                        ImageView imgTmp = null;
                        int position = arrMark.get(index).getId();
                        if (position == 0) {
                            imgTmp = image1;
                        } else if (position == 1) {
                            imgTmp = image2;
                        } else if (position == 2) {
                            imgTmp = image3;
                        } else if (position == 3) {
                            imgTmp = image4;
                        } else if (position == 4) {
                            imgTmp = image5;
                        } else if (position == 5) {
                            imgTmp = image6;
                        } else if (position == 6) {
                            imgTmp = image7;
                        } else if (position == 7) {
                            imgTmp = image8;
                        } else if (position == 8) {
                            imgTmp = image9;
                        }

                        selectBox(imgTmp, position + 1, playerTurn);

                    }
                }
            };
            if (!checkPlayerWin(playerUniqueId)) {
                handler.postDelayed(runnable, 800);
            }

        }
    }

    private ArrayList<Mark> solver(String img) {
        ArrayList<Mark> arrPoints = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            if (boxesSelectedTmp.get(i).equals("")) {
                if (img.equals("imgO")) {
                    boxesSelectedTmp.set(i, computerId);
                } else {
                    boxesSelectedTmp.set(i, playerUniqueId);
                }

                if (checkWinTmp(img) == -100) {
                    if (img.equals("imgO")) {
                        depth++;
                        ArrayList<Mark> arr = solver("imgX");
                        depth--;

                        int minimum = 50;
                        int index = 50;
                        for (int j = 0; j < arr.size(); j++) {
                            if (minimum > arr.get(j).getPoint()) {
                                minimum = arr.get(j).getPoint();
                                index = i;
                            }
                        }
                        if (minimum != 50 && index != 50) {
                            arrPoints.add(new Mark(index, minimum));
                        }
                    } else if (img.equals("imgX")) {
                        depth++;
                        ArrayList<Mark> arr = solver("imgO");
                        depth--;
                        int maximum = -50;
                        int index = -50;
                        for (int j = 0; j < arr.size(); j++) {
                            if (maximum < arr.get(j).getPoint()) {
                                maximum = arr.get(j).getPoint();
                                index = i;
                            }
                        }
                        if (maximum != -50 && index != -50) {
                            arrPoints.add(new Mark(index, maximum));
                        }
                    }
                } else {
                    if (img.equals("imgO")) {
                        arrPoints.add(new Mark(i, checkWinTmp("imgO") - depth));
                    } else if (img.equals("imgX")){
                        arrPoints.add(new Mark(i, -checkWinTmp("imgX") + depth));
                    }
                }
                boxesSelectedTmp.set(i, "");
            }
        }
//        Log.v("arrPoints", String.valueOf(arrPoints));
        return arrPoints;

    }

    private int checkWinTmp(String img) {
        final String playerId = img.equals("imgO") ? computerId : playerUniqueId;
        for (int i = 0; i < combinationList.size(); i++) {

            final int[] combination = combinationList.get(i);

            if (boxesSelectedTmp.get(combination[0]).equals(playerId) && boxesSelectedTmp.get(combination[1]).equals(playerId) && boxesSelectedTmp.get(combination[2]).equals(playerId)) {
                return 10;
            }
        }

        int count = 0;
        for (int i = 0; i < 9; i++) {
            if (!boxesSelectedTmp.get(i).equals("")) {
                count++;
            }
        }
        if (count == 9) {
            return 0;
        }
        return -100;
    }

    private void selectBox(ImageView imageView, int selectedBoxPosition, String selectedByPlayer) {
        boxesSelectedBy.set(selectedBoxPosition - 1, selectedByPlayer);
        if (selectedByPlayer.equals(playerUniqueId)) {
            imageView.setImageResource(R.drawable.cross_icon);
            playerTurn = computerId;
        } else {
            imageView.setImageResource(R.drawable.zero_icon);
            playerTurn = playerUniqueId;
        }
        if (checkPlayerWin(selectedByPlayer)) {
            String message = selectedByPlayer.equals(playerUniqueId) ? "You won!" : "Computer won!";
            final WinDialogComputerMode winDialog = new WinDialogComputerMode(ComputerMode.this, message);
            winDialog.setCancelable(false);
            winDialog.show();
        }
        doneBoxes.add(selectedBoxPosition);
        // over the game if there is no box left
        if (doneBoxes.size() == 9) {
            final WinDialogComputerMode winDialog = new WinDialogComputerMode(ComputerMode.this, "It is a Draw!");
            winDialog.setCancelable(false);
            winDialog.show();
        }
        applyPlayerTurn(playerTurn);
    }

    private void applyPlayerTurn(String playerId) {
        if (playerId.equals(playerUniqueId)) {
            player1Layout.setBackgroundResource(R.drawable.round_back_dark_blue_stroke);
            player2Layout.setBackgroundResource(R.drawable.round_back_dark_blue_20);
        } else {
            player1Layout.setBackgroundResource(R.drawable.round_back_dark_blue_20);
            player2Layout.setBackgroundResource(R.drawable.round_back_dark_blue_stroke);
        }
    }

    private boolean checkPlayerWin(String playerId) {
        boolean isPlayerWon = false;
        for (int i = 0; i < combinationList.size(); i++) {

            final int[] combination = combinationList.get(i);

            if (boxesSelectedBy.get(combination[0]).equals(playerId) && boxesSelectedBy.get(combination[1]).equals(playerId) && boxesSelectedBy.get(combination[2]).equals(playerId)) {
                isPlayerWon = true;
                break;
            }
        }
        return isPlayerWon;
    }
}