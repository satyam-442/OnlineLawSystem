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

public final class ActivityEditLawsBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final MaterialButton addLawBtn;

  @NonNull
  public final TextInputLayout editLawDescription;

  @NonNull
  public final Spinner editLawJurisdictionSpinner;

  @NonNull
  public final TextView editLawJurisdictionText;

  @NonNull
  public final TextInputLayout editLawSection;

  @NonNull
  public final TextInputLayout editLawTitle;

  private ActivityEditLawsBinding(@NonNull LinearLayout rootView, @NonNull MaterialButton addLawBtn,
      @NonNull TextInputLayout editLawDescription, @NonNull Spinner editLawJurisdictionSpinner,
      @NonNull TextView editLawJurisdictionText, @NonNull TextInputLayout editLawSection,
      @NonNull TextInputLayout editLawTitle) {
    this.rootView = rootView;
    this.addLawBtn = addLawBtn;
    this.editLawDescription = editLawDescription;
    this.editLawJurisdictionSpinner = editLawJurisdictionSpinner;
    this.editLawJurisdictionText = editLawJurisdictionText;
    this.editLawSection = editLawSection;
    this.editLawTitle = editLawTitle;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityEditLawsBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityEditLawsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_edit_laws, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityEditLawsBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.addLawBtn;
      MaterialButton addLawBtn = ViewBindings.findChildViewById(rootView, id);
      if (addLawBtn == null) {
        break missingId;
      }

      id = R.id.editLawDescription;
      TextInputLayout editLawDescription = ViewBindings.findChildViewById(rootView, id);
      if (editLawDescription == null) {
        break missingId;
      }

      id = R.id.editLawJurisdictionSpinner;
      Spinner editLawJurisdictionSpinner = ViewBindings.findChildViewById(rootView, id);
      if (editLawJurisdictionSpinner == null) {
        break missingId;
      }

      id = R.id.editLawJurisdictionText;
      TextView editLawJurisdictionText = ViewBindings.findChildViewById(rootView, id);
      if (editLawJurisdictionText == null) {
        break missingId;
      }

      id = R.id.editLawSection;
      TextInputLayout editLawSection = ViewBindings.findChildViewById(rootView, id);
      if (editLawSection == null) {
        break missingId;
      }

      id = R.id.editLawTitle;
      TextInputLayout editLawTitle = ViewBindings.findChildViewById(rootView, id);
      if (editLawTitle == null) {
        break missingId;
      }

      return new ActivityEditLawsBinding((LinearLayout) rootView, addLawBtn, editLawDescription,
          editLawJurisdictionSpinner, editLawJurisdictionText, editLawSection, editLawTitle);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}