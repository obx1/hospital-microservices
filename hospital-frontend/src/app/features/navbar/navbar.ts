import { Component } from '@angular/core';
import { RouterLink, RouterLinkActive } from '@angular/router';

@Component({
  selector: 'app-navbar',
  standalone: true,  // Add this line
  imports: [RouterLink, RouterLinkActive],  // Add these for routing
  templateUrl: './navbar.html',
  styleUrl: './navbar.css',
})
export class Navbar {

}
