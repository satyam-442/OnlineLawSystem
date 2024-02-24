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

public final class RecyclerLawLayoutBinding implements ViewBinding {
  @NonNull
  private final CardView rootView;

  @NonNull
  public final TextView recyclerLawDescription;

  @NonNull
  public final TextView recyclerLawJurisdiction;

  @NonNull
  public final TextView recyclerLawSection;

  @NonNull
  public final TextView recyclerLawTitle;

  private RecyclerLawLayoutBinding(@NonNull CardView rootView,
      @NonNull TextView recyclerLawDescription, @NonNull TextView recyclerLawJurisdiction,
      @NonNull TextView recyclerLawSection, @NonNull TextView recyclerLawTitle) {
    this.rootView = rootView;
    this.recyclerLawDescription = recyclerLawDescription;
    this.recyclerLawJurisdiction = recyclerLawJurisdiction;
    this.recyclerLawSection = recyclerLawSection;
    this.recyclerLawTitle = recyclerLawTitle;
  }

  @Override
  @NonNull
  public CardView getRoot() {
    return rootView;
  }

  @NonNull
  public static RecyclerLawLayoutBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static RecyclerLawLayoutBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.recycler_law_layout, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static RecyclerLawLayoutBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.recyclerLawDescription;
      TextView recyclerLawDescription = ViewBindings.findChildViewById(rootView, id);
      if (recyclerLawDescription == null) {
        break missingId;
      }

      id = R.id.recyclerLawJurisdiction;
      TextView recyclerLawJurisdiction = ViewBindings.findChildViewById(rootView, id);
      if (recyclerLawJurisdiction == null) {
        break missingId;
      }

      id = R.id.recyclerLawSection;
      TextView recyclerLawSection = ViewBindings.findChildViewById(rootView, id);
      if (recyclerLawSection == null) {
        break missingId;
      }

      id = R.id.recyclerLawTitle;
      TextView recyclerLawTitle = ViewBindings.findChildViewById(rootView, id);
      if (recyclerLawTitle == null) {
        break missingId;
      }

      return new RecyclerLawLayoutBinding((CardView) rootView, recyclerLawDescription,
          recyclerLawJurisdiction, recyclerLawSection, recyclerLawTitle);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
