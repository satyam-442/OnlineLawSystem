// Generated by view binder compiler. Do not edit!
package com.example.onlinelawsystem.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.onlinelawsystem.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class RecyclerCriminalLayoutBinding implements ViewBinding {
  @NonNull
  private final CardView rootView;

  @NonNull
  public final TextView recyclerCriminalAddress;

  @NonNull
  public final TextView recyclerCriminalAlias;

  @NonNull
  public final TextView recyclerCriminalCaseDetails;

  @NonNull
  public final TextView recyclerCriminalDOB;

  @NonNull
  public final TextView recyclerCriminalGender;

  @NonNull
  public final TextView recyclerCriminalName;

  private RecyclerCriminalLayoutBinding(@NonNull CardView rootView,
      @NonNull TextView recyclerCriminalAddress, @NonNull TextView recyclerCriminalAlias,
      @NonNull TextView recyclerCriminalCaseDetails, @NonNull TextView recyclerCriminalDOB,
      @NonNull TextView recyclerCriminalGender, @NonNull TextView recyclerCriminalName) {
    this.rootView = rootView;
    this.recyclerCriminalAddress = recyclerCriminalAddress;
    this.recyclerCriminalAlias = recyclerCriminalAlias;
    this.recyclerCriminalCaseDetails = recyclerCriminalCaseDetails;
    this.recyclerCriminalDOB = recyclerCriminalDOB;
    this.recyclerCriminalGender = recyclerCriminalGender;
    this.recyclerCriminalName = recyclerCriminalName;
  }

  @Override
  @NonNull
  public CardView getRoot() {
    return rootView;
  }

  @NonNull
  public static RecyclerCriminalLayoutBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static RecyclerCriminalLayoutBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.recycler_criminal_layout, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static RecyclerCriminalLayoutBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.recyclerCriminalAddress;
      TextView recyclerCriminalAddress = ViewBindings.findChildViewById(rootView, id);
      if (recyclerCriminalAddress == null) {
        break missingId;
      }

      id = R.id.recyclerCriminalAlias;
      TextView recyclerCriminalAlias = ViewBindings.findChildViewById(rootView, id);
      if (recyclerCriminalAlias == null) {
        break missingId;
      }

      id = R.id.recyclerCriminalCaseDetails;
      TextView recyclerCriminalCaseDetails = ViewBindings.findChildViewById(rootView, id);
      if (recyclerCriminalCaseDetails == null) {
        break missingId;
      }

      id = R.id.recyclerCriminalDOB;
      TextView recyclerCriminalDOB = ViewBindings.findChildViewById(rootView, id);
      if (recyclerCriminalDOB == null) {
        break missingId;
      }

      id = R.id.recyclerCriminalGender;
      TextView recyclerCriminalGender = ViewBindings.findChildViewById(rootView, id);
      if (recyclerCriminalGender == null) {
        break missingId;
      }

      id = R.id.recyclerCriminalName;
      TextView recyclerCriminalName = ViewBindings.findChildViewById(rootView, id);
      if (recyclerCriminalName == null) {
        break missingId;
      }

      return new RecyclerCriminalLayoutBinding((CardView) rootView, recyclerCriminalAddress,
          recyclerCriminalAlias, recyclerCriminalCaseDetails, recyclerCriminalDOB,
          recyclerCriminalGender, recyclerCriminalName);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
