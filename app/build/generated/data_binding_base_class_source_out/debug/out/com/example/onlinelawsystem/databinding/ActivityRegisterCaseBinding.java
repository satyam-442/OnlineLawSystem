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

public final class ActivityRegisterCaseBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final MaterialButton addCaseBtn;

  @NonNull
  public final TextInputLayout caseDetail;

  @NonNull
  public final TextView courtSpinnerText;

  @NonNull
  public final Spinner expertiseSpinner;

  @NonNull
  public final TextView expertiseSpinnerText;

  @NonNull
  public final TextInputLayout userAddress;

  @NonNull
  public final TextInputLayout userAge;

  @NonNull
  public final TextInputLayout userContact;

  @NonNull
  public final TextInputLayout userFullname;

  private ActivityRegisterCaseBinding(@NonNull LinearLayout rootView,
      @NonNull MaterialButton addCaseBtn, @NonNull TextInputLayout caseDetail,
      @NonNull TextView courtSpinnerText, @NonNull Spinner expertiseSpinner,
      @NonNull TextView expertiseSpinnerText, @NonNull TextInputLayout userAddress,
      @NonNull TextInputLayout userAge, @NonNull TextInputLayout userContact,
      @NonNull TextInputLayout userFullname) {
    this.rootView = rootView;
    this.addCaseBtn = addCaseBtn;
    this.caseDetail = caseDetail;
    this.courtSpinnerText = courtSpinnerText;
    this.expertiseSpinner = expertiseSpinner;
    this.expertiseSpinnerText = expertiseSpinnerText;
    this.userAddress = userAddress;
    this.userAge = userAge;
    this.userContact = userContact;
    this.userFullname = userFullname;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityRegisterCaseBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityRegisterCaseBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_register_case, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityRegisterCaseBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.addCaseBtn;
      MaterialButton addCaseBtn = ViewBindings.findChildViewById(rootView, id);
      if (addCaseBtn == null) {
        break missingId;
      }

      id = R.id.caseDetail;
      TextInputLayout caseDetail = ViewBindings.findChildViewById(rootView, id);
      if (caseDetail == null) {
        break missingId;
      }

      id = R.id.courtSpinnerText;
      TextView courtSpinnerText = ViewBindings.findChildViewById(rootView, id);
      if (courtSpinnerText == null) {
        break missingId;
      }

      id = R.id.expertiseSpinner;
      Spinner expertiseSpinner = ViewBindings.findChildViewById(rootView, id);
      if (expertiseSpinner == null) {
        break missingId;
      }

      id = R.id.expertiseSpinnerText;
      TextView expertiseSpinnerText = ViewBindings.findChildViewById(rootView, id);
      if (expertiseSpinnerText == null) {
        break missingId;
      }

      id = R.id.userAddress;
      TextInputLayout userAddress = ViewBindings.findChildViewById(rootView, id);
      if (userAddress == null) {
        break missingId;
      }

      id = R.id.userAge;
      TextInputLayout userAge = ViewBindings.findChildViewById(rootView, id);
      if (userAge == null) {
        break missingId;
      }

      id = R.id.userContact;
      TextInputLayout userContact = ViewBindings.findChildViewById(rootView, id);
      if (userContact == null) {
        break missingId;
      }

      id = R.id.userFullname;
      TextInputLayout userFullname = ViewBindings.findChildViewById(rootView, id);
      if (userFullname == null) {
        break missingId;
      }

      return new ActivityRegisterCaseBinding((LinearLayout) rootView, addCaseBtn, caseDetail,
          courtSpinnerText, expertiseSpinner, expertiseSpinnerText, userAddress, userAge,
          userContact, userFullname);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
