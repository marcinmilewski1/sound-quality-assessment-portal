import {CanActivate, RouterStateSnapshot, ActivatedRouteSnapshot, Router} from "@angular/router";
import {Injectable} from "@angular/core";
import {AuthService} from "../auth/auth.service";

@Injectable()
export class UserGuard implements CanActivate {
    constructor(private authService: AuthService, private router: Router) {
    }

    canActivate(next: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        if (this.authService.isAuthenticated() && this.authService.hasRole('ROLE_USER')) {
            return true;
        }
        this.router.navigate(['accessDenied']);
        return false;
    }

}