package com.example.newproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FAQFragment extends Fragment {

    ExpandableListView expandableListView;
    FAQExpandableListAdapter listAdapter;
    List<String> listDataHeader;
    HashMap<String, String> listDataChild;
    Button askQuestionButton;
    EditText askQuestionInput;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_f_a_q, container, false);

        // Get the views
        expandableListView = view.findViewById(R.id.faq_expandable_list);
        askQuestionButton = view.findViewById(R.id.btn_ask_question);
        askQuestionInput = view.findViewById(R.id.et_ask_question);

        // Preparing FAQ list data
        prepareListData();

        listAdapter = new FAQExpandableListAdapter(getContext(), listDataHeader, listDataChild);
        expandableListView.setAdapter(listAdapter);

        // Handle "Ask Question" button click
        askQuestionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newQuestion = askQuestionInput.getText().toString();

                if (!newQuestion.isEmpty()) {
                    listDataHeader.add(newQuestion);
                    listDataChild.put(newQuestion, "Answer will be updated soon.");
                    listAdapter.notifyDataSetChanged();

                    // Save the new question in SharedPreferences
                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("FAQPrefs", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("question_" + listDataHeader.size(), newQuestion);
                    editor.putString("answer_" + listDataHeader.size(), "Answer will be updated soon.");
                    editor.apply();

                    askQuestionInput.setText(""); // Clear input field
                    Toast.makeText(getContext(), "Question added", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Please enter a question", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    // Method to prepare FAQ list data
    private void prepareListData() {
        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<>();

        // Load stored FAQs from SharedPreferences
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("FAQPrefs", Context.MODE_PRIVATE);
        Map<String, ?> allEntries = sharedPreferences.getAll(); // Get all stored entries
        int size = allEntries.size() / 2; // Assuming each question has one answer

        // Load all the questions and answers
        for (int i = 0; i < size; i++) {
            String question = sharedPreferences.getString("question_" + i, null);
            String answer = sharedPreferences.getString("answer_" + i, null);

            if (question != null && answer != null) {
                listDataHeader.add(question);
                listDataChild.put(question, answer);
            }
        }

        // Add default FAQ items if needed
        if (listDataHeader.isEmpty()) {
            listDataHeader.add("What is the return policy?");
            listDataHeader.add("How can I track my order?");
            listDataHeader.add("What payment methods are available?");
            listDataHeader.add("How do I cancel an order?");

            listDataChild.put(listDataHeader.get(0), "Our return policy lasts 30 days...");
            listDataChild.put(listDataHeader.get(1), "You can track your order via the tracking page...");
            listDataChild.put(listDataHeader.get(2), "We accept Visa, MasterCard, and PayPal...");
            listDataChild.put(listDataHeader.get(3), "You can cancel an order within 24 hours...");
        }
    }
}
