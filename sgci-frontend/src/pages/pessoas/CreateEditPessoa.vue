<template>
  <div>
    <q-form greedy ref="formPessoa" @submit="cadastrar" class="bg">
      <div class="bg"></div>
      <div class="main-container">
        <div class="q-mb-md">
          <h4 class="title">Cadastrar Pessoa</h4>
          <div class="divisor-inline"></div>
        </div>

        <!-- Campos Básicos de Pessoa -->
        <div class="q-pa-md" style="margin-top: -30px;">
          <h4 class="subTitulo">Dados Básicos</h4>
          <div class="row q-col-gutter-lg q-mb-lg">
            <div class="col-6">
              <q-input :rules="[val => !(val == null || val == '') || 'Campo Obrigatório']" rounded outlined
                v-model="pessoa.nome" label="Nome" dense />
            </div>
            <div class="col-2">
              <q-input :rules="[val => !(val == null || val == '') || 'Campo Obrigatório']" rounded outlined
                v-model="pessoa.documento" label="Documento" dense />
            </div>
            <div class="col-4">
              <q-input :rules="[val => !(val == null || val == '') || 'Campo Obrigatório']" rounded outlined
                v-model="pessoa.profissao" label="Profissão" dense />
            </div>
          </div>

          <div class="row q-col-gutter-lg" style="margin-top: -15px;">
            <div class="col-md-5">
              <q-field dense label="Tipo pessoa"
                lazy-rules borderless stack-label>
                <template #control>
                  <q-option-group v-model="pessoa.tipoPessoa" :options="optionsTipoPessoa" type="radio" size="xs" inline />
                </template>
              </q-field>
            </div>
            <div class="col-md-7">
              <q-field dense label="Estado Civil"
                lazy-rules borderless stack-label>
                <template #control>
                  <q-option-group v-model="pessoa.estadoCivil" :options="optionsEstadoCivil" type="radio" size="xs" inline />
                </template>
              </q-field>
            </div>
          </div>
        </div>
        <!--Campos endereço-->
        <div class="q-pa-md" style="margin-top: -30px;">
          <h4 class="subTitulo">Endereço</h4>
          <div class="row q-col-gutter-lg q-mb-lg">
            <div class="col-2">
              <q-input :rules="[val => !(val == null || val == '') || 'Campo Obrigatório']" rounded outlined
                v-model="pessoa.endereco.cep" label="CEP" dense />
            </div>
            <div class="col-3">
              <q-input :rules="[val => !(val == null || val == '') || 'Campo Obrigatório']" rounded outlined
                v-model="pessoa.endereco.estado" label="Estado" dense />
            </div>
            <div class="col-4">
              <q-input :rules="[val => !(val == null || val == '') || 'Campo Obrigatório']" rounded outlined
                v-model="pessoa.endereco.cidade" label="Cidade" dense />
            </div>
            <div class="col-2">
              <q-input :rules="[val => !(val == null || val == '') || 'Campo Obrigatório']" rounded outlined
                v-model="pessoa.endereco.bairro" label="Bairro" dense />
            </div>
            <div class="col-9">
              <q-input :rules="[val => !(val == null || val == '') || 'Campo Obrigatório']" rounded outlined
                v-model="pessoa.endereco.rua" label="Rua" dense />
            </div>
            <div class="col-2">
              <q-input :rules="[val => !(val == null || val == '') || 'Campo Obrigatório']" rounded outlined
                v-model="pessoa.endereco.numero" label="Número" dense />
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-12">
            <div style="float: right;">
              <!-- Botões -->
              <q-btn style="margin-right: 10px;" label="Voltar" no-caps class="btn-voltar"></q-btn>
              <q-btn type="submit" label="Cadastrar" no-caps class="btn-cadastrar"></q-btn>
            </div>
          </div>
        </div>
      </div>
    </q-form>
  </div>
</template>

<script>
import { ref } from 'vue';
import { pessoaService } from '@/services/sgciApiService.js'

export default {
  name: 'CreateEditPessoa',
  // No setup é onde ficam declaradas as variaveis que serão usadas no Script
  setup () {
    // Variavel para os dados de pessoa, utilizando ref para dizer que é uma variavel que receberá
    // valores dinamicos da tela
    const pessoa = ref({
      nome: null,
      documento: null,
      profissao: null,
      tipoPessoa: null,
      estadoCivil: null,
      endereco: ref({
        cep: null,
        estado: null,
        cidade: null,
        rua: null,
        bairro: null,
        numero: null
      })
    })
    return {
      pessoa,
      optionsTipoPessoa: [
        {
          label: 'Pessoa Física',
          value: 'PESSOA_FISICA'
        },
        {
          label: 'Pessoa Jurídica',
          value: 'PESSOA_JURIDICA'
        }
      ],
      optionsEstadoCivil: [
        {
          label: 'Solteiro',
          value: 'SOLTEIRO'
        },
        {
          label: 'Casado',
          value: 'CASADO'
        },
        {
          label: 'Separado',
          value: 'SEPARADO'
        },
        {
          label: 'Divorciado',
          value: 'DIVORCIADO'
        },
        {
          label: 'Viuvo',
          value: 'VIUVO'
        }
      ]
    }
  },

  methods: {
    cadastrar () {
      pessoaService.create(this.pessoa).then(response => {
        console.log(`Pessoa Cadastrada com sucesso BE respondeu: ${response}`)
      })
    }
  }
}

</script>
