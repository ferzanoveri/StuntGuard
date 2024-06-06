// Generated by view binder compiler. Do not edit!
package com.example.stuntguard.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.stuntguard.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityLoginBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final Button btnLogin;

  @NonNull
  public final TextView btnRegister;

  @NonNull
  public final LinearLayout containerMisc;

  @NonNull
  public final TextInputEditText emailEditText;

  @NonNull
  public final ConstraintLayout formLogin;

  @NonNull
  public final TextView labelRegister;

  @NonNull
  public final ImageView logo;

  @NonNull
  public final TextInputLayout outlinedTextFieldEmail;

  @NonNull
  public final TextInputLayout outlinedTextFieldPass;

  @NonNull
  public final TextInputEditText passEditText;

  @NonNull
  public final ProgressBar progressBar;

  @NonNull
  public final TextView tvSignInGreet1;

  @NonNull
  public final TextView tvSignInGreet2;

  @NonNull
  public final TextView tvSignInGreet3;

  private ActivityLoginBinding(@NonNull ConstraintLayout rootView, @NonNull Button btnLogin,
      @NonNull TextView btnRegister, @NonNull LinearLayout containerMisc,
      @NonNull TextInputEditText emailEditText, @NonNull ConstraintLayout formLogin,
      @NonNull TextView labelRegister, @NonNull ImageView logo,
      @NonNull TextInputLayout outlinedTextFieldEmail,
      @NonNull TextInputLayout outlinedTextFieldPass, @NonNull TextInputEditText passEditText,
      @NonNull ProgressBar progressBar, @NonNull TextView tvSignInGreet1,
      @NonNull TextView tvSignInGreet2, @NonNull TextView tvSignInGreet3) {
    this.rootView = rootView;
    this.btnLogin = btnLogin;
    this.btnRegister = btnRegister;
    this.containerMisc = containerMisc;
    this.emailEditText = emailEditText;
    this.formLogin = formLogin;
    this.labelRegister = labelRegister;
    this.logo = logo;
    this.outlinedTextFieldEmail = outlinedTextFieldEmail;
    this.outlinedTextFieldPass = outlinedTextFieldPass;
    this.passEditText = passEditText;
    this.progressBar = progressBar;
    this.tvSignInGreet1 = tvSignInGreet1;
    this.tvSignInGreet2 = tvSignInGreet2;
    this.tvSignInGreet3 = tvSignInGreet3;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityLoginBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityLoginBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_login, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityLoginBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btnLogin;
      Button btnLogin = ViewBindings.findChildViewById(rootView, id);
      if (btnLogin == null) {
        break missingId;
      }

      id = R.id.btn_register;
      TextView btnRegister = ViewBindings.findChildViewById(rootView, id);
      if (btnRegister == null) {
        break missingId;
      }

      id = R.id.container_misc;
      LinearLayout containerMisc = ViewBindings.findChildViewById(rootView, id);
      if (containerMisc == null) {
        break missingId;
      }

      id = R.id.emailEditText;
      TextInputEditText emailEditText = ViewBindings.findChildViewById(rootView, id);
      if (emailEditText == null) {
        break missingId;
      }

      id = R.id.form_login;
      ConstraintLayout formLogin = ViewBindings.findChildViewById(rootView, id);
      if (formLogin == null) {
        break missingId;
      }

      id = R.id.label_register;
      TextView labelRegister = ViewBindings.findChildViewById(rootView, id);
      if (labelRegister == null) {
        break missingId;
      }

      id = R.id.logo;
      ImageView logo = ViewBindings.findChildViewById(rootView, id);
      if (logo == null) {
        break missingId;
      }

      id = R.id.outlinedTextFieldEmail;
      TextInputLayout outlinedTextFieldEmail = ViewBindings.findChildViewById(rootView, id);
      if (outlinedTextFieldEmail == null) {
        break missingId;
      }

      id = R.id.outlinedTextFieldPass;
      TextInputLayout outlinedTextFieldPass = ViewBindings.findChildViewById(rootView, id);
      if (outlinedTextFieldPass == null) {
        break missingId;
      }

      id = R.id.passEditText;
      TextInputEditText passEditText = ViewBindings.findChildViewById(rootView, id);
      if (passEditText == null) {
        break missingId;
      }

      id = R.id.progress_bar;
      ProgressBar progressBar = ViewBindings.findChildViewById(rootView, id);
      if (progressBar == null) {
        break missingId;
      }

      id = R.id.tv_signInGreet1;
      TextView tvSignInGreet1 = ViewBindings.findChildViewById(rootView, id);
      if (tvSignInGreet1 == null) {
        break missingId;
      }

      id = R.id.tv_signInGreet2;
      TextView tvSignInGreet2 = ViewBindings.findChildViewById(rootView, id);
      if (tvSignInGreet2 == null) {
        break missingId;
      }

      id = R.id.tv_signInGreet3;
      TextView tvSignInGreet3 = ViewBindings.findChildViewById(rootView, id);
      if (tvSignInGreet3 == null) {
        break missingId;
      }

      return new ActivityLoginBinding((ConstraintLayout) rootView, btnLogin, btnRegister,
          containerMisc, emailEditText, formLogin, labelRegister, logo, outlinedTextFieldEmail,
          outlinedTextFieldPass, passEditText, progressBar, tvSignInGreet1, tvSignInGreet2,
          tvSignInGreet3);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
