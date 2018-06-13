import {PolymerElement} from '@polymer/polymer/polymer-element.js';

// Define the class for a new element called custom-element
class CustomElement extends PolymerElement {

    constructor() {
        super();
        this.textContent = 'I\'m a custom-element.';
        console.log(this.tagName);
    }


    static get template() {
        return html`
        <!-- scoped CSS for this element -->
        <!--Note: The CSS styles defined inside the <dom-module> are scoped to the element's shadow DOM. So the div rule here only affects <div> tags inside <custom-element>.-->
        <style>
            div {
            display: inline-block;
            background-color: #ccc;
            border-radius: 8px;
            padding: 4px;
            }
        </style>

        <p>I'm a DOM element. This is my shadow DOM!</p>

        <!-- TODO: Try adding some other html elements inside thetemplate. 
           For example, add <h1>A heading!</h1> or<a href="stuff.html">A link!</a>
        -->
        
        
        <!-- TODO: Try adding other HTML elements to the DOM template to see how they are positioned relative to the distributed child nodes. -->
        <div>
        <!-- any children are rendered here -->
        <slot></slot>
        </div>
        
        `;
    }


}

// Register the new element with the browser
// The custom element's name must start with an ASCII letter and contain a dash (-).
customElements.define('custom-element', CustomElement);

/*
  If you’re familiar with your browser’s developer tools, try printing the
  custom element’s `tagName` property to the console.
  Hint: add `console.log(this.tagName);` to the constructor method!
*/
