import {Injectable} from '@angular/core';
import {HttpErrorResponse, HttpStatusCode} from '@angular/common/http';

import {throwError} from 'rxjs';

@Injectable(
)
export class ErrorHandlerService {

  constructor() {
  }

  public handleError(error: HttpErrorResponse) {
    if (error.error instanceof ErrorEvent) {
      console.error('An error occurred:', error.error.message);
      alert('An error occurred: ' + error.error.message)
    } else {
      if (error.status === HttpStatusCode.Unauthorized || error.status === HttpStatusCode.Forbidden) {
        window.location.href = '/login';
      } else {
        console.error(
          `Backend returned code ${error.status}, ` +
          `body was: ${error.error}`);
        alert(`${error.error}`)
      }
    }
    return throwError(
      'Something bad happened; please try again later.');
  };

}
