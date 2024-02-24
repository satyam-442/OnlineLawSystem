// Generated by view binder compiler. Do not edit!
package com.example.onlinelawsystem.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.onlinelawsystem.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityReadCaseStudyBinding implements ViewBinding {
  @NonNull
  private final ScrollView rootView;

  @NonNull
  public final TextView caseStudyCourtJurisdiction;

  @NonNull
  public final TextView caseStudyDescription;

  @NonNull
  public final TextView caseStudyLegalIssues;

  @NonNull
  public final TextView caseStudyTitle;

  @NonNull
  public final TextView caseStudyVerdict;

  private ActivityReadCaseStudyBinding(@NonNull ScrollView rootView,
      @NonNull TextView caseStudyCourtJurisdiction, @NonNull TextView caseStudyDescription,
      @NonNull TextView caseStudyLegalIssues, @NonNull TextView caseStudyTitle,
      @NonNull TextView caseStudyVerdict) {
    this.rootView = rootView;
    this.caseStudyCourtJurisdiction = caseStudyCourtJurisdiction;
    this.caseStudyDescription = caseStudyDescription;
    this.caseStudyLegalIssues = caseStudyLegalIssues;
    this.caseStudyTitle = caseStudyTitle;
    this.caseStudyVerdict = caseStudyVerdict;
  }

  @Override
  @NonNull
  public ScrollView getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityReadCaseStudyBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityReadCaseStudyBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_read_case_study, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityReadCaseStudyBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.caseStudyCourtJurisdiction;
      TextView caseStudyCourtJurisdiction = ViewBindings.findChildViewById(rootView, id);
      if (caseStudyCourtJurisdiction == null) {
        break missingId;
      }

      id = R.id.caseStudyDescription;
      TextView caseStudyDescription = ViewBindings.findChildViewById(rootView, id);
      if (caseStudyDescription == null) {
        break missingId;
      }

      id = R.id.caseStudyLegalIssues;
      TextView caseStudyLegalIssues = ViewBindings.findChildViewById(rootView, id);
      if (caseStudyLegalIssues == null) {
        break missingId;
      }

      id = R.id.caseStudyTitle;
      TextView caseStudyTitle = ViewBindings.findChildViewById(rootView, id);
      if (caseStudyTitle == null) {
        break missingId;
      }

      id = R.id.caseStudyVerdict;
      TextView caseStudyVerdict = ViewBindings.findChildViewById(rootView, id);
      if (caseStudyVerdict == null) {
        break missingId;
      }

      return new ActivityReadCaseStudyBinding((ScrollView) rootView, caseStudyCourtJurisdiction,
          caseStudyDescription, caseStudyLegalIssues, caseStudyTitle, caseStudyVerdict);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
