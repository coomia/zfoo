import {PolymerElement} from '@polymer/polymer/polymer-element.js';

// Define the class for a new element called custom-element
class CustomElement extends PolymerElement {

    // Shadow DOM is encapsulated inside the element.
    // ShadowDOM最大的用处应该是隔离外部环境用于封装组件,它有一种既定的样式，外部css不会影响到它
    // Polymer's DOM templating to create a shadow DOM tree for your element.
    static get template () {
        return html`
        <p>I'm a DOM element. This is my shadow DOM!</p>

        <!-- TODO: Try adding some other html elements inside the template. 
        For example, add <h1>A heading!</h1> or <a href="stuff.html">A link!</a>
        -->
        `;
    }
}

// Register the new element
customElements.define('custom-element', CustomElement);