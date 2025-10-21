import $ from "cash-dom";

export default class App {
  static initialize() {
    $(document).ready(() => {
      const myElement = $("#main-header");
      if (myElement.length) {
        // Check if element exists
        myElement.addClass("active");
        myElement.on("click", (event: Event) => {
          console.log("Element clicked:", event.target);
        });
      }

      console.log("App initialized");
    });
  }
}
