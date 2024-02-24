// Generated by view binder compiler. Do not edit!
package com.example.onlinelawsystem.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.onlinelawsystem.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityEditCourtBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final MaterialButton courtEditBtn;

  @NonNull
  public final TextInputLayout editCourtAddress;

  @NonNull
  public final TextView editCourtCategoryText;

  @NonNull
  public final TextInputLayout editCourtCity;

  @NonNull
  public final TextInputLayout editCourtContact;

  @NonNull
  public final TextInputLayout editCourtName;

  @NonNull
  public final Spinner editCourtTypeSpinner;

  private ActivityEditCourtBinding(@NonNull LinearLayout rootView,
      @NonNull MaterialButton courtEditBtn, @NonNull TextInputLayout editCourtAddress,
      @NonNull TextView editCourtCategoryText, @NonNull TextInputLayout editCourtCity,
      @NonNull TextInputLayout editCourtContact, @NonNull TextInputLayout editCourtName,
      @NonNull Spinner editCourtTypeSpinner) {
    this.rootView = rootView;
    this.courtEditBtn = courtEditBtn;
    this.editCourtAddress = editCourtAddress;
    this.editCourtCategoryText = editCourtCategoryText;
    this.editCourtCity = editCourtCity;
    this.editCourtContact = editCourtContact;
    this.editCourtName = editCourtName;
    this.editCourtTypeSpinner = editCourtTypeSpinner;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityEditCourtBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityEditCourtBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_edit_court, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityEditCourtBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.courtEditBtn;
      MaterialButton courtEditBtn = ViewBindings.findChildViewById(rootView, id);
      if (courtEditBtn == null) {
        break missingId;
      }

      id = R.id.editCourtAddress;
      TextInputLayout editCourtAddress = ViewBindings.findChildViewById(rootView, id);
      if (editCourtAddress == null) {
        break missingId;
      }

      id = R.id.editCourtCategoryText;
      TextView editCourtCategoryText = ViewBindings.findChildViewById(rootView, id);
      if (editCourtCategoryText == null) {
        break missingId;
      }

      id = R.id.editCourtCity;
      TextInputLayout editCourtCity = ViewBindings.findChildViewById(rootView, id);
      if (editCourtCity == null) {
        break missingId;
      }

      id = R.id.editCourtContact;
      TextInputLayout editCourtContact = ViewBindings.findChildViewById(rootView, id);
      if (editCourtContact == null) {
        break missingId;
      }

      id = R.id.editCourtName;
      TextInputLayout editCourtName = ViewBindings.findChildViewById(rootView, id);
      if (editCourtName == null) {
        break missingId;
      }

      id = R.id.editCourtTypeSpinner;
      Spinner editCourtTypeSpinner = ViewBindings.findChildViewById(rootView, id);
      if (editCourtTypeSpinner == null) {
        break missingId;
      }

      return new ActivityEditCourtBinding((LinearLayout) rootView, courtEditBtn, editCourtAddress,
          editCourtCategoryText, editCourtCity, editCourtContact, editCourtName,
          editCourtTypeSpinner);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}