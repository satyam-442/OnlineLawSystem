// Generated by view binder compiler. Do not edit!
package com.example.onlinelawsystem.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
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

public final class ActivityAdminLoginBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final MaterialButton adminLoginButton;

  @NonNull
  public final TextInputLayout adminLoginEmail;

  @NonNull
  public final TextInputLayout adminLoginPassword;

  private ActivityAdminLoginBinding(@NonNull RelativeLayout rootView,
      @NonNull MaterialButton adminLoginButton, @NonNull TextInputLayout adminLoginEmail,
      @NonNull TextInputLayout adminLoginPassword) {
    this.rootView = rootView;
    this.adminLoginButton = adminLoginButton;
    this.adminLoginEmail = adminLoginEmail;
    this.adminLoginPassword = adminLoginPassword;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityAdminLoginBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityAdminLoginBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_admin_login, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityAdminLoginBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.adminLoginButton;
      MaterialButton adminLoginButton = ViewBindings.findChildViewById(rootView, id);
      if (adminLoginButton == null) {
        break missingId;
      }

      id = R.id.adminLoginEmail;
      TextInputLayout adminLoginEmail = ViewBindings.findChildViewById(rootView, id);
      if (adminLoginEmail == null) {
        break missingId;
      }

      id = R.id.adminLoginPassword;
      TextInputLayout adminLoginPassword = ViewBindings.findChildViewById(rootView, id);
      if (adminLoginPassword == null) {
        break missingId;
      }

      return new ActivityAdminLoginBinding((RelativeLayout) rootView, adminLoginButton,
          adminLoginEmail, adminLoginPassword);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}