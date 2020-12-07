import '@polymer/polymer/lib/elements/custom-style.js';
const documentContainer = document.createElement('template');

documentContainer.innerHTML = `
    <custom-style>
        <style>
            /* global styles */
            .logo-link {
                display: flex;
                outline: none;
                margin-left: 0.5em;
            }
            .logo {
                height: 64px;
            }
            .avatar-data {
                margin-top: -0.5em;
            }
        </style>

        <dom-module theme-for="vaadin-form-item" id="custom-form-item">
            <template>
                <style>
                    :host {
                        --vaadin-form-item-row-spacing: 1.5em;
                        --vaadin-form-item-label-width: 6em;
                    }
                </style>
            </template>
        </dom-module>

    </custom-style>`;

document.head.appendChild(documentContainer.content);
