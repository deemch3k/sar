<template>
    <v-layout row>
        <v-text-field
                label="new message"
                placeholder="Write smth"
                v-model="text"
                @keyup.enter="save"
        />
        <v-btn @click="save">
            Save
        </v-btn>
    </v-layout>
</template>

<script>

    import {mapActions} from 'vuex'
    import * as Sentry from '@sentry/browser'


    export default {

        props: ['messageAttr'],
        data: function () {
            return {
                text: '',
                id: null
            }

        },
        watch: {
            messageAttr(newVal, OldVal) {
                this.text = newVal.text
                this.id = newVal.id
            }
        },
        methods: {
            ...mapActions(['addMessageAction', 'updateMessageAction']),
            save() {
                if (this.id) {
                    this.updateMessageAction({
                        id: this.id,
                        text: this.text
                    });
                } else {
                    this.addMessageAction({
                        text: this.text
                    });
                }

                this.text = ''
                this.id = null
            }
        }
    }
</script>

<style>

</style>