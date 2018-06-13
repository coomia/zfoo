import {PolymerElement} from '@polymer/polymer/polymer-element.js';

// Define the class for a new element called custom-element
class CustomElement extends PolymerElement {

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
        <!-- TODO: Try adding other HTML elements to the DOM template to see how they are positioned relative to the distributed child nodes. -->
        
        <!--Shadow DOM lets you control composition. The element's children can be distributed so they render as if they were inserted into the shadow DOM tree.-->
        <!--This example creates a simple tag that decorates an image by wrapping it with a styled <div> tag.-->
        <div>
        <!-- any children are rendered here -->
        <!--这是一个占位符-->
        <slot></slot>
        </div>
        `;
    }
}

// Register the new element
customElements.define('custom-element', CustomElement);