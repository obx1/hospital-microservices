import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-reset-password',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './reset-password.html',
  styleUrls: ['./reset-password.css']
})
export class ResetPasswordPage {

  form = {
    newPassword: '',
    confirmPassword: ''
  };

  resetPassword() {
    if (this.form.newPassword !== this.form.confirmPassword) {
      alert("Passwords do not match!");
      return;
    }

    console.log("Resetting password:", this.form.newPassword);
    // TODO: backend call
  }
}
