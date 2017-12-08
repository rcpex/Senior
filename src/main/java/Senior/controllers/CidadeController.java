package Senior.controllers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
	@ResponseBody
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
	@ResponseBody
	public List<Cidade> getCapitais() {	
		return repository.getCapitaisOrdenadasPorNome();
	}
	
	@RequestMapping("/maior-menor-qtde")
	@ResponseBody
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
	@ResponseBody
	public Map<String, Long> getQtdeCidadePorEstado() {
		
		Map<String, Long> result  = new HashMap<String, Long>();
		
		for(Object[] aux : repository.getQtdeCidadesPorEstado()) {
			result.put((String) aux[0], (Long) aux[1]);
		}

		return result;
	}
	
	@RequestMapping("/cidade-por-ibge")
	@ResponseBody
	public Cidade getCidadePorIbge(String ibgeId) {		
		return repository.findByIbgeId(ibgeId);
	}
	
	@RequestMapping("/cidades-por-uf")
	@ResponseBody
	public List<Cidade> getCidadesPorEstado(String uf) {		
		return repository.findByUf(uf);
	}
	
	@RequestMapping("/salvar-cidade")
	@ResponseBody
	public Cidade salvarCidade(Cidade cidade) {		
		return repository.save(cidade);
	}
	
	@RequestMapping("/excluir-cidade")
	public void excluirCidade(Cidade cidade) {		
		repository.delete(cidade);
	}
	
	@RequestMapping("/cidades-por-filtro")
	@ResponseBody
	public List<Cidade> getRegistrosPorColuna(String coluna, String filtro) {
	
		if(coluna.equals("ibgeId")) {
			return repository.getCidadesPorIbgeId(filtro);
		} else if(coluna.equals("uf")) {
			return repository.getCidadesPorEstado(filtro);
		} else if(coluna.equals("name")) {
			return repository.getCidadesPorName(filtro);
		} else if(coluna.equals("alternativeNames")) {
			return repository.getCidadesPorAlternativeNames(filtro);
		} else if(coluna.equals("noAccents")) {
			return repository.getCidadesPorNoAccents(filtro);
		} else if(coluna.equals("microregion")) {
			return repository.getCidadesPorMicroregion(filtro);
		} else if(coluna.equals("mesoregion")) {
			return repository.getCidadesPorMesoregion(filtro);
		}		
			
		return null;
	}
		
	@RequestMapping("/qtde-registros-coluna")
	@ResponseBody
	public Map<String, Long> getQtdeRegistrosPorColuna(String coluna) {	
		
		Map<String, Long> result  = new HashMap<String, Long>();
		
		if(coluna.equals("uf")) {
			for(Object[] aux : repository.getQtdeCidadesPorEstado()) {
				result.put((String) aux[0], (Long) aux[1]);
			}
		} else if(coluna.equals("microregion")) {
			for(Object[] aux : repository.getQtdeCidadesPorMicroregion()) {
				result.put((String) aux[0], (Long) aux[1]);
			}
		} else if(coluna.equals("mesoregion")) {
			for(Object[] aux : repository.getQtdeCidadesPorMesoregion()) {
				result.put((String) aux[0], (Long) aux[1]);
			}
		} else if(coluna.equals("name")) {
			for(Object[] aux : repository.getQtdeCidadesPorName()) {
				result.put((String) aux[0], (Long) aux[1]);
			}
		} else {
			result.put("Modifique sua busca para as colunas name, uf, microregion ou mesoregion", new Long(0));
		}
		
		return result;
	}
	
	@RequestMapping("/qtde-registros")
	@ResponseBody
	public Map<String, String> getQtdeRegistros() {		
		Map<String, String> result = new HashMap<>();
		
		result.put("qtde-registros", String.valueOf(repository.getQtdeRegistros()));
		return result;
	}
	
	@RequestMapping("/maior-distancia")
	@ResponseBody
	public Map<String, String> getMaiorDistancia() {		
		double cat1, cat2, hip, dist=0;
		Cidade cidade1 = null, cidade2 = null;
		
		List<Cidade> cidades = new ArrayList<>();
		cidades = repository.findAll();
		
		int i = 0, j = 1;
		while(i < cidades.size()) {
			
			cat1 = Math.pow(cidades.get(j).getLon() - cidades.get(i).getLon(), 2);
			cat2 = Math.pow(cidades.get(j).getLat() - cidades.get(i).getLat(), 2);
			hip = Math.sqrt(cat1+cat2);

			if(dist < hip) {
				dist = hip;
				cidade1 = cidades.get(i);
				cidade2 = cidades.get(j);
			}
			
			i++;
			
			if (i == cidades.size()) {
				j++;
				i = j+1; 
			}
		}			
		
		String maiorDistancia = cidade1.getName() + ", " + cidade1.getUf() 
								+ " - " 
								+ cidade2.getName()+ ", " + cidade2.getUf();

		Map<String, String> result = new HashMap<>();
		result.put("Maior distancia", maiorDistancia);
		
		return result;
	}
}