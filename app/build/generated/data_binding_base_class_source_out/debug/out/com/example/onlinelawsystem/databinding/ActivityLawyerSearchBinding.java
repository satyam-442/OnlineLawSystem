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

public final class ActivityLawyerSearchBinding implements ViewBinding {
  @NonNull
  private final FrameLayout rootView;

  @NonNull
  public final LinearLayout emptyLawyerLayout;

  @NonNull
  public final LinearLayout lawyerAvailableLayout;

  @NonNull
  public final RecyclerView lawyerRecyclerView;

  @NonNull
  public final TextInputLayout searchView;

  private ActivityLawyerSearchBinding(@NonNull FrameLayout rootView,
      @NonNull LinearLayout emptyLawyerLayout, @NonNull LinearLayout lawyerAvailableLayout,
      @NonNull RecyclerView lawyerRecyclerView, @NonNull TextInputLayout searchView) {
    this.rootView = rootView;
    this.emptyLawyerLayout = emptyLawyerLayout;
    this.lawyerAvailableLayout = lawyerAvailableLayout;
    this.lawyerRecyclerView = lawyerRecyclerView;
    this.searchView = searchView;
  }

  @Override
  @NonNull
  public FrameLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityLawyerSearchBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityLawyerSearchBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_lawyer_search, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityLawyerSearchBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.emptyLawyerLayout;
      LinearLayout emptyLawyerLayout = ViewBindings.findChildViewById(rootView, id);
      if (emptyLawyerLayout == null) {
        break missingId;
      }

      id = R.id.lawyerAvailableLayout;
      LinearLayout lawyerAvailableLayout = ViewBindings.findChildViewById(rootView, id);
      if (lawyerAvailableLayout == null) {
        break missingId;
      }

      id = R.id.lawyerRecyclerView;
      RecyclerView lawyerRecyclerView = ViewBindings.findChildViewById(rootView, id);
      if (lawyerRecyclerView == null) {
        break missingId;
      }

      id = R.id.searchView;
      TextInputLayout searchView = ViewBindings.findChildViewById(rootView, id);
      if (searchView == null) {
        break missingId;
      }

      return new ActivityLawyerSearchBinding((FrameLayout) rootView, emptyLawyerLayout,
          lawyerAvailableLayout, lawyerRecyclerView, searchView);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
