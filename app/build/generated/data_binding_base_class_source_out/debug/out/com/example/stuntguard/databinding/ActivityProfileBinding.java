// Generated by view binder compiler. Do not edit!
package com.example.stuntguard.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.stuntguard.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityProfileBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final BottomNavigationView bottomNavigationMain;

  @NonNull
  public final CardView card;

  @NonNull
  public final LinearLayout editKeluarga;

  @NonNull
  public final LinearLayout logout;

  @NonNull
  public final TextView tvName;

  @NonNull
  public final LinearLayout ubahPassword;

  @NonNull
  public final LinearLayout ubahProfile;

  @NonNull
  public final TextView yourProfile;

  private ActivityProfileBinding(@NonNull ConstraintLayout rootView,
      @NonNull BottomNavigationView bottomNavigationMain, @NonNull CardView card,
      @NonNull LinearLayout editKeluarga, @NonNull LinearLayout logout, @NonNull TextView tvName,
      @NonNull LinearLayout ubahPassword, @NonNull LinearLayout ubahProfile,
      @NonNull TextView yourProfile) {
    this.rootView = rootView;
    this.bottomNavigationMain = bottomNavigationMain;
    this.card = card;
    this.editKeluarga = editKeluarga;
    this.logout = logout;
    this.tvName = tvName;
    this.ubahPassword = ubahPassword;
    this.ubahProfile = ubahProfile;
    this.yourProfile = yourProfile;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityProfileBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityProfileBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_profile, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityProfileBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.bottom_navigation_main;
      BottomNavigationView bottomNavigationMain = ViewBindings.findChildViewById(rootView, id);
      if (bottomNavigationMain == null) {
        break missingId;
      }

      id = R.id.card;
      CardView card = ViewBindings.findChildViewById(rootView, id);
      if (card == null) {
        break missingId;
      }

      id = R.id.edit_keluarga;
      LinearLayout editKeluarga = ViewBindings.findChildViewById(rootView, id);
      if (editKeluarga == null) {
        break missingId;
      }

      id = R.id.logout;
      LinearLayout logout = ViewBindings.findChildViewById(rootView, id);
      if (logout == null) {
        break missingId;
      }

      id = R.id.tv_name;
      TextView tvName = ViewBindings.findChildViewById(rootView, id);
      if (tvName == null) {
        break missingId;
      }

      id = R.id.ubah_password;
      LinearLayout ubahPassword = ViewBindings.findChildViewById(rootView, id);
      if (ubahPassword == null) {
        break missingId;
      }

      id = R.id.ubah_profile;
      LinearLayout ubahProfile = ViewBindings.findChildViewById(rootView, id);
      if (ubahProfile == null) {
        break missingId;
      }

      id = R.id.your_profile;
      TextView yourProfile = ViewBindings.findChildViewById(rootView, id);
      if (yourProfile == null) {
        break missingId;
      }

      return new ActivityProfileBinding((ConstraintLayout) rootView, bottomNavigationMain, card,
          editKeluarga, logout, tvName, ubahPassword, ubahProfile, yourProfile);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
