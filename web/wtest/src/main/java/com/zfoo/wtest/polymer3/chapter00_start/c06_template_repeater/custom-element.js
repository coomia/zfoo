//import the Polymer library
import {PolymerElement, html} from '@polymer/polymer/polymer-element.js'

//import the template repeater
import '@polymer/polymer/lib/elements/dom-repeat.js'

// Define the class for a new element called custom-element
class CustomElement extends PolymerElement {
    constructor() {
        super();
        /* TODO:
         * - Change the first and last names inside this.employees
         * - Add another employee by inserting another object
         *   into the array definition after Tony Mori:
         *   {first: 'Shawna', last: 'Williams'}
         *   Remember to make sure your commas are correct!
         */

        this.employees = [
            {first: 'Bob', last: 'Li'},
            {first: 'Ayesha', last: 'Johnson'},
            {first: 'Fatma', last: 'Kumari'},
            {first: 'Tony', last: 'Mori'}
        ];
    }

    static get template() {
        return html`
        <div> Employee list: </div>
        <p></p>
        <template is="dom-repeat" items="{{employees}}">
            <div>First name: <span>{{item.first}}</span></div>
            <div>Last name: <span>{{item.last}}</span></div>
            <p></p>
        </template>
    `;
    }
}

// Register the new element
customElements.define('custom-element', CustomElement);