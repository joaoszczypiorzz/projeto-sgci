import axios from "axios";

const apiSgci = axios.create({
  baseURL: import.meta.env.VITE_API_SGCI
});

export class PessoaService {
  path = '/pessoas/create'

  create (pessoa) {
    return apiSgci.post(this.path, pessoa)
  }
}

export const pessoaService = new PessoaService()