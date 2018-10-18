import {Injectable} from "@angular/core";

@Injectable({
  providedIn: 'root'
})
export class ErrorMessage {
  private map: Map<string, string> = new Map<string, string>()
    .set("INTERNAL_ERROR", "Unknown error occured. Please try again.")
    .set("INVALID_COUNTRY_ISO_CODE", "Invalid iso country have been passed. Please try again.")
    .set("COUNTRY_NOT_SUPPORTED", "Unfortunately we don't support this country.")
    .set("INVALID_NUMBER_FORMAT", "Invalid format of earnings have been passed. Please try again.");

  public getOrDefault(key: string): string {
    if (this.map.has(key)) {
      return this.map.get(key);
    } else return "Unknown error occured. Please try again."
  }
}
