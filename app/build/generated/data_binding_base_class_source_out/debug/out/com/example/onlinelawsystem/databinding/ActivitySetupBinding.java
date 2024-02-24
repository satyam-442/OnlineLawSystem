// Generated by view binder compiler. Do not edit!
package com.example.onlinelawsystem.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
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

public final class ActivitySetupBinding implements ViewBinding {
  @NonNull
  private final ScrollView rootView;

  @NonNull
  public final RadioGroup genderGroup;

  @NonNull
  public final TextInputLayout setupAddress;

  @NonNull
  public final MaterialButton setupBtn;

  @NonNull
  public final TextInputLayout setupCity;

  @NonNull
  public final RadioButton setupFemale;

  @NonNull
  public final TextInputLayout setupFirstName;

  @NonNull
  public final TextInputLayout setupGender;

  @NonNull
  public final TextInputLayout setupLastName;

  @NonNull
  public final RadioButton setupMale;

  @NonNull
  public final TextInputLayout setupMiddleName;

  @NonNull
  public final RadioButton setupOther;

  @NonNull
  public final TextInputLayout setupPhone;

  private ActivitySetupBinding(@NonNull ScrollView rootView, @NonNull RadioGroup genderGroup,
      @NonNull TextInputLayout setupAddress, @NonNull MaterialButton setupBtn,
      @NonNull TextInputLayout setupCity, @NonNull RadioButton setupFemale,
      @NonNull TextInputLayout setupFirstName, @NonNull TextInputLayout setupGender,
      @NonNull TextInputLayout setupLastName, @NonNull RadioButton setupMale,
      @NonNull TextInputLayout setupMiddleName, @NonNull RadioButton setupOther,
      @NonNull TextInputLayout setupPhone) {
    this.rootView = rootView;
    this.genderGroup = genderGroup;
    this.setupAddress = setupAddress;
    this.setupBtn = setupBtn;
    this.setupCity = setupCity;
    this.setupFemale = setupFemale;
    this.setupFirstName = setupFirstName;
    this.setupGender = setupGender;
    this.setupLastName = setupLastName;
    this.setupMale = setupMale;
    this.setupMiddleName = setupMiddleName;
    this.setupOther = setupOther;
    this.setupPhone = setupPhone;
  }

  @Override
  @NonNull
  public ScrollView getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivitySetupBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivitySetupBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_setup, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivitySetupBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.genderGroup;
      RadioGroup genderGroup = ViewBindings.findChildViewById(rootView, id);
      if (genderGroup == null) {
        break missingId;
      }

      id = R.id.setupAddress;
      TextInputLayout setupAddress = ViewBindings.findChildViewById(rootView, id);
      if (setupAddress == null) {
        break missingId;
      }

      id = R.id.setupBtn;
      MaterialButton setupBtn = ViewBindings.findChildViewById(rootView, id);
      if (setupBtn == null) {
        break missingId;
      }

      id = R.id.setupCity;
      TextInputLayout setupCity = ViewBindings.findChildViewById(rootView, id);
      if (setupCity == null) {
        break missingId;
      }

      id = R.id.setupFemale;
      RadioButton setupFemale = ViewBindings.findChildViewById(rootView, id);
      if (setupFemale == null) {
        break missingId;
      }

      id = R.id.setupFirstName;
      TextInputLayout setupFirstName = ViewBindings.findChildViewById(rootView, id);
      if (setupFirstName == null) {
        break missingId;
      }

      id = R.id.setupGender;
      TextInputLayout setupGender = ViewBindings.findChildViewById(rootView, id);
      if (setupGender == null) {
        break missingId;
      }

      id = R.id.setupLastName;
      TextInputLayout setupLastName = ViewBindings.findChildViewById(rootView, id);
      if (setupLastName == null) {
        break missingId;
      }

      id = R.id.setupMale;
      RadioButton setupMale = ViewBindings.findChildViewById(rootView, id);
      if (setupMale == null) {
        break missingId;
      }

      id = R.id.setupMiddleName;
      TextInputLayout setupMiddleName = ViewBindings.findChildViewById(rootView, id);
      if (setupMiddleName == null) {
        break missingId;
      }

      id = R.id.setupOther;
      RadioButton setupOther = ViewBindings.findChildViewById(rootView, id);
      if (setupOther == null) {
        break missingId;
      }

      id = R.id.setupPhone;
      TextInputLayout setupPhone = ViewBindings.findChildViewById(rootView, id);
      if (setupPhone == null) {
        break missingId;
      }

      return new ActivitySetupBinding((ScrollView) rootView, genderGroup, setupAddress, setupBtn,
          setupCity, setupFemale, setupFirstName, setupGender, setupLastName, setupMale,
          setupMiddleName, setupOther, setupPhone);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
