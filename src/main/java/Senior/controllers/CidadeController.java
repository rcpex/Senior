package Senior.controllers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import Senior.models.Cidade;
import Senior.repository.CidadeRepository;

@Controller
@RequestMapping(value="/cidade")
public class CidadeController {

	private CidadeRepository repository;
	
	public CidadeController(CidadeRepository repository) {
		this.repository = repository;
	}
	
	@RequestMapping("/carrega-db")
	public void carregarCidades() {		
		String arquivo =  "C:/Users/Renan/Desktop/Trabalho Java - Cidades.csv";
	    BufferedReader br = null;
	    String linha = "", divisor = ",";
	    try {

	        br = new BufferedReader(new FileReader(arquivo));
	        linha = br.readLine();
	        while ((linha = br.readLine()) != null) {
	        	
	            String[] cidadeAux = linha.split(divisor);
	            gravarCidades(cidadeAux);      
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    } 
	    
	    if (br != null) {
	    	try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	    }
	}

	public void gravarCidades(String[] cidadeAux) {
		Cidade cidade;
			
		cidade = new Cidade();
		
		cidade.setIbgeId(cidadeAux[0]);
		cidade.setUf(cidadeAux[1]);
		cidade.setName(cidadeAux[2]);
		cidade.setCapital(new Boolean(cidadeAux[3]));	
		cidade.setLon(Double.parseDouble(cidadeAux[4]));
		cidade.setLat(Double.parseDouble(cidadeAux[5]));		
		cidade.setNoAccents(cidadeAux[6]);
		cidade.setAlternativeNames(cidadeAux[7]);
		cidade.setMicroregion(cidadeAux[8]);
		cidade.setMesoregion(cidadeAux[9]);

		cidade = repository.save(cidade);	
	}
	
	@RequestMapping("/capitais")
	public List<Cidade> getCapitais() {	
		return repository.getCapitaisOrdenadasPorNome();
	}
	
	@RequestMapping("/maior-menor-qtde")
	public Map<String, Long> getUfMaiorMenorQtdeCidade() {
	
		List<Object[]> qtdeCidadePorEstado = repository.getQtdeCidadesPorEstado();
		Map<String, Long> ufQtdeMaiorMenor  = new HashMap<String, Long>();
		
		boolean primeiro = true;
		long maior = 0, menor = 0;
		String ufMaior="", ufMenor="";
		
		for(Object[] ufQtde : qtdeCidadePorEstado) {
			String uf = (String) ufQtde[0];
			long qtde = (long) ufQtde[1];
			
			if(primeiro) {
				menor = qtde;
				maior = qtde;
				primeiro = false;
			}
			
			if(qtde > maior) {
				maior = qtde;
				ufMaior = uf;
			}
			
			if(qtde < menor) {
				menor = qtde;
				ufMenor = uf;
			}
		}
		
		ufQtdeMaiorMenor.put(ufMenor, menor);
		ufQtdeMaiorMenor.put(ufMaior, maior);
		
		return ufQtdeMaiorMenor;
	}
	
	@RequestMapping("/qtde-cidades")
	public Map<String, Long> getQtdeCidadePorEstado() {
		
		Map<String, Long> ufQtdeCidades  = new HashMap<String, Long>();
		
		for(Object[] aux : repository.getQtdeCidadesPorEstado()) {
			ufQtdeCidades.put((String) aux[0], (Long) aux[1]);
		}

		return ufQtdeCidades;
	}
	
	@RequestMapping("/cidade-por-ibge")
	public Cidade getCidadePorIbge(String ibgeId) {		
		return repository.findByIbgeId(ibgeId);
	}
	
	@RequestMapping("/cidades-por-uf")
	public List<Cidade> getCidadesPorEstado(String uf) {		
		return repository.findByUf(uf);
	}
	
	@RequestMapping("/salvar-cidade")
	public Cidade salvarCidade(Cidade cidade) {		
		return repository.save(cidade);
	}
	
	@RequestMapping("/excluir-cidade")
	public void excluirCidade(Cidade cidade) {		
		repository.delete(cidade);
	}
	
	@RequestMapping("/cidades-por-filtro")
	public List<Cidade> getRegistrosPorColuna(String coluna, String filtro) {	
		return repository.getCidadesPorFiltro(coluna, filtro);
	}
	
	@RequestMapping("/qtde-registros-coluna")
	public String getRegistrosPorColuna(String coluna) {		
		return String.valueOf(repository.getQtdeRegistrosPorColuna(coluna));
	}
	
	@RequestMapping("/qtde-registros")
	public String getQtdeRegistros() {		
		System.out.println(String.valueOf(repository.getQtdeRegistros()));
		return String.valueOf(repository.getQtdeRegistros());
	}
	
	@RequestMapping("/maior-distancia")
	public String getMaiorDistancia() {		
		double cat1, cat2, hip, dist=0;
		Cidade cidade1 = null, cidade2 = null;
		
		List<Cidade> cidades1 = repository.findAll();
		List<Cidade> cidades2 = repository.findAll();
		
		for(Cidade c1 : cidades1) {		
			for(Cidade c2 : cidades2) {
				cat1 = Math.pow(c1.getLon() - c2.getLon(), 2);
				cat2 = Math.pow(c1.getLat() - c2.getLat(), 2);
				hip = Math.sqrt(cat1+cat2);
				if(dist < hip) {
					dist = hip;
					cidade1 = c1;
					cidade2 = c2;
				}
			}			
		}
		
		String maiorDistancia = cidade1.getName() + " - " + cidade2.getName();
		return maiorDistancia;
	}
}