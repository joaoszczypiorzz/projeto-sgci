<template>
  <div>
    <q-form greedy ref="formPessoa" @submit="cadastrar" class="bg">
      <div class="bg"></div>
      <div class="main-container">
        <div class="q-mb-md">
          <h4 class="title">Cadastrar Pessoa</h4>
          <div class="divisor-inline"></div>
        </div>

        <!-- Campos do Formulário de Pessoa -->
        <div class="q-pa-md">
          <div class="row q-col-gutter-lg q-mb-sm">
             <div class="col-6">
               <q-input rounded outlined v-model="pessoa.nome" label="Nome" dense />
            </div>
            <div class="col-2">
               <q-input rounded outlined v-model="pessoa.documento" label="Documento" dense />
            </div>
            <div class="col-4">
               <q-input rounded outlined v-model="pessoa.profissao" label="Profissão" dense />
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-12">
            <div style="float: right;">
              <!-- Botões -->
               <q-btn style="margin-right: 10px;" label="Voltar" no-caps class="btn-voltar"></q-btn>
               <q-btn  type="button" @click="cadastrar()" label="Cadastrar" no-caps class="btn-cadastrar"></q-btn>
            </div>
          </div>
        </div>
      </div>
    </q-form>
  </div>
</template>

<script>
import { ref } from 'vue';

export default {
  name: 'CreateEditPessoa',
  // No setup é onde ficam declaradas as variaveis que serão usadas no Script
  setup () {
    // Variavel para os dados de pessoa, utilizando ref para dizer que é uma variavel que receberá
    // valores dinamicos da tela
    const pessoa = ref({
      nome: null,
      documento: null,
      profissao: null
    })
    return {
      pessoa
    }
  },

  methods: {
     cadastrar () {
      console.log('Invocou o metodo cadastrar')
    }
  }
}

</script>
