// Generated by view binder compiler. Do not edit!
package com.example.onlinelawsystem.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.onlinelawsystem.R;
import com.google.android.material.textfield.TextInputLayout;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityLawSearchBinding implements ViewBinding {
  @NonNull
  private final FrameLayout rootView;

  @NonNull
  public final LinearLayout emptyLawLayout;

  @NonNull
  public final LinearLayout lawAvailableLayout;

  @NonNull
  public final RecyclerView lawRecyclerView;

  @NonNull
  public final TextInputLayout searchView;

  private ActivityLawSearchBinding(@NonNull FrameLayout rootView,
      @NonNull LinearLayout emptyLawLayout, @NonNull LinearLayout lawAvailableLayout,
      @NonNull RecyclerView lawRecyclerView, @NonNull TextInputLayout searchView) {
    this.rootView = rootView;
    this.emptyLawLayout = emptyLawLayout;
    this.lawAvailableLayout = lawAvailableLayout;
    this.lawRecyclerView = lawRecyclerView;
    this.searchView = searchView;
  }

  @Override
  @NonNull
  public FrameLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityLawSearchBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityLawSearchBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_law_search, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityLawSearchBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.emptyLawLayout;
      LinearLayout emptyLawLayout = ViewBindings.findChildViewById(rootView, id);
      if (emptyLawLayout == null) {
        break missingId;
      }

      id = R.id.lawAvailableLayout;
      LinearLayout lawAvailableLayout = ViewBindings.findChildViewById(rootView, id);
      if (lawAvailableLayout == null) {
        break missingId;
      }

      id = R.id.lawRecyclerView;
      RecyclerView lawRecyclerView = ViewBindings.findChildViewById(rootView, id);
      if (lawRecyclerView == null) {
        break missingId;
      }

      id = R.id.searchView;
      TextInputLayout searchView = ViewBindings.findChildViewById(rootView, id);
      if (searchView == null) {
        break missingId;
      }

      return new ActivityLawSearchBinding((FrameLayout) rootView, emptyLawLayout,
          lawAvailableLayout, lawRecyclerView, searchView);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
