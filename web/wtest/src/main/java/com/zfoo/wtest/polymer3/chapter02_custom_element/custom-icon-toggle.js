import {PolymerElement, html} from '@polymer/polymer/polymer-element.js';
import '@polymer/iron-icon/iron-icon.js';
import '@polymer/iron-icons/iron-icons.js';

class CustomIconToggle extends PolymerElement {
    static get template() {
        return html`


        <style>

            :host {
                /*未选中的颜色*/
                --icon-toggle-color: lightgrey;
                /*选中的颜色*/
                --icon-toggle-pressed-color: red;
                /*边缘的轮廓颜色*/
                --icon-toggle-outline-color: black;
                /*字体*/
                font-family: sans-serif;
            }

            :host {
                display: inline-block;
            }

            :host iron-icon {
                fill: var(--icon-toggle-color, rgba(0, 0, 0, 0));
                stroke: var(--icon-toggle-outline-color, currentcolor);
            }

            :host([pressed]) iron-icon {
                fill: var(--icon-toggle-pressed-color, currentcolor);
            }
        </style>
        
        <h2>custom-icon-toggle:</h2>

        <iron-icon icon="[[toggleIcon]]" on-click="toggle"></iron-icon>


        `;
    }

    static get is() {
        return 'custom-icon-toggle';
    }

    // To declare properties, add a static properties getter to the element's class. The getter should return an object containing property declarations.
    // In most cases, a property that's part of your element's public API should be declared in the properties object.
    static get properties() {
        return {
            pressed: {
                type: Boolean,
                notify: true,
                reflectToAttribute: true,   // 当为false就不会通知其它的属性更新，既不会回调pressedChanged方法
                value: false,
            },
            toggleIcon: {
                type: String
            }
        };
    }


    static get observers() {
        return [
            'pressedChanged(pressed)'
        ]
    }

    pressedChanged(pressed) {
        alert("pressed=" + pressed);
    }

    toggle() {
        this.pressed = !this.pressed;
    }


    // ************************************分割线————下面是生命周期回调函数*************************************************************
    // Polymer序列化的规则：
    // String. No serialization required.
    // Date or Number. Serialized using toString.
    // Array or Object. Serialized using JSON.stringify.
    // Boolean. Results in a non-valued attribute to be either set (true) or removed (false)
    // pressed属性因为是boolean值，所以输出为空，当没有这个属性输出null
    attributeChangedCallback(name, old, value) {
        super.attributeChangedCallback(name, old, value);
        console.log("attributeChanged callback: attributeName-->" + name + ", oldValue-->" + old + ", newValue-->" + value);
    }

    // 元素被创建时调用，限制：必须是无参构造器；必须调用super();不能return；尽量在connectedCallback()写初始化代码
    constructor() {
        super();
        console.log("This is constructor");
    }

    // 元素第一次被添加到document时调用：1.创建shadow dom；2.初始化属性；3.初始化观察者observer和动态计算属性computed properties
    // 限制：不能在这个方法里操作动态属性的值，可以用observer或computed properties
    ready() {
        // 因为父类的ready()做了一些初始化工作，所以必须要调用super.ready();
        super.ready();
        console.log("ready callback");
    }


    // 当被创建的元素标签被添加到document中，你就可以用document.getElementById()找到
    connectedCallback() {
        super.connectedCallback();
        console.log("connected callback");
        // console.log("-->"+this.userName+this.age);
    }

    disconnectedCallback() {
        super.disconnectedCallback();
        console.log("disconnected callback");
    }
}

customElements.define(CustomIconToggle.is, CustomIconToggle);
