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

            /*:host is a pseudo-class selector that matches the "host" element of <icon-toggle>s shadow DOM-that is, the <icon-toggle> element itself.*/
            /*icon-toogle的伪类选择器，选择的是自己*/
            :host {
                display: inline-block;
            }

            /*The <iron-icon> tag uses an SVG icon. */
            /*The fill and stroke properties are SVG-specific CSS properties. They set the fill color and the outline color for the icon, respectively.*/
            :host iron-icon {
                /*自定义属性必须以 '--' 开始.*/
                /*通过var函数引用，第一个参数是自定义属性，第二个是默认属性*/
                fill: var(--icon-toggle-color, rgba(0, 0, 0, 0));
                stroke: var(--icon-toggle-outline-color, currentcolor);
            }

            /*The :host() function matches the host element if the selector inside the parentheses matches the host element. */
            /*In this case, [pressed] is a standard CSS attribute selector, so this rule matches when the icon-toggle has a pressed attribute set on it.*/
            /*属性选择器，如果当前元素有pressed属性，则选择成功，false代表没有这个属性*/
            :host([pressed]) iron-icon {
                fill: var(--icon-toggle-pressed-color, currentcolor);
            }
        </style>

        <!--The <iron-icon> element renders the icon specified by its icon attribute. -->
        <!--可以自己蒂尼icon属性，star代表星星，favor代表心号-->
        <iron-icon icon="[[toggleIcon]]" on-click="toggle"></iron-icon>


        `;
    }

    // You then give your new element a name, so that the browser can recognize it when you use it in tags.
    // This name must match the id given in your element's template definition (<dom-module id="icon-toggle">).
    static get is() {
        return 'custom-icon-toggle';
    }


    // Learn more: notify and reflectToAttribute. The notify and reflectToAttribute properties may sound similar:
    // they both make the element's state visible to the outside world. reflectToAttribute makes the state visible in the DOM tree, ' +
    // 'so that it's visible to CSS and the querySelector methods. notify makes state changes observable outside the element,
    // either using JavaScript event handlers or Polymer two-way data binding.
    static get properties() {
        return {
            pressed: {
                type: Boolean,             // 变量的类型
                notify: true,              // 当这个值变化的时候以同步事件的形式广播出去，对当前dom之外的元素也有影响，如双向数据绑定，下章会说，现在不懂没关系
                reflectToAttribute: true,  // 让这个值对当前dom tree可见，既当前dom中和这个有关的值会随着它变化而变化，如icon-toggle[pressed]
                value: false,              // 默认值
            },
            toggleIcon: {
                type: String
            }
        };
    }

    constructor() {
        // Note that you must always call super() as the first line of code if you override the constructor.
        super();
    }

    // Observe the pressed property on the object
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
}

customElements.define(CustomIconToggle.is, CustomIconToggle);