package course.com.androidchat.addcontact.ui;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.ButterKnife;
import course.com.androidchat.R;
import course.com.androidchat.addcontact.AddContactPresenter;
import course.com.androidchat.addcontact.AddContactPresenterImpl;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddContactFragment extends DialogFragment implements AddContactView, DialogInterface.OnShowListener {

    @Bind(R.id.editTxtEmail) EditText editTxtEmail;
    @Bind(R.id.progressBar) ProgressBar progressBar;
    private AddContactPresenter presenter;

    public AddContactFragment() {
        // Required empty public constructor
        presenter = new AddContactPresenterImpl(this);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_add_contact, null);
        ButterKnife.bind(this, view);

        AlertDialog.Builder builder = createBuilder();
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.setOnShowListener(this);
        return dialog;
    }

    @NonNull private AlertDialog.Builder createBuilder() {
        return new AlertDialog.Builder(getActivity())
              .setTitle(R.string.addcontact_message_title)
              .setPositiveButton(R.string.addcontact_message_add, createOnClickListenerForAddButton())
              .setNegativeButton(R.string.addcontact_message_cancel, createOnClickListenerForCancelButton());
    }

    private DialogInterface.OnClickListener createOnClickListenerForCancelButton() {
        return new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        };
    }

    private DialogInterface.OnClickListener createOnClickListenerForAddButton() {
        return new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        };
    }

    @Override
    public void showInput() {
        editTxtEmail.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideInput() {
        editTxtEmail.setVisibility(View.GONE);
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void contactAdded() {
        Toast.makeText(getActivity(), R.string.addcontact_message_contactAdded, Toast.LENGTH_SHORT).show();
        dismiss();
    }

    @Override
    public void contactNotAdded() {
        editTxtEmail.setText("");
        editTxtEmail.setError(getString(R.string.addcontact_message_error));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onShow(DialogInterface dialogInterface) {
        AlertDialog dialog = (AlertDialog) getDialog();
        if (dialog != null) {
            Button positiveButton = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
            Button negativeButton = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);

            positiveButton.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View view) {
                    presenter.addContact(editTxtEmail.getText().toString());
                }
            });

            negativeButton.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View view) {
                    dismiss();
                }
            });
        }
        presenter.onShow();
    }

    @Override
    public void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }
}
