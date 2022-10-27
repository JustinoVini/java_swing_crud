package br.com.vinicius.gui;

import javax.swing.JOptionPane;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import br.com.vinicius.dao.ProductDAO;
import br.com.vinicius.entities.Product;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class JFramePrincipal {

	protected Shell shlGestoDeProdutos;
	private Table tableProduct;
	private Text IdTxt;
	private Text txtNome;
	private Text txtPreco;
	private Text txtQuantidade;
	private Text txtDescricao;
	private ProductDAO pm = new ProductDAO();

	/**
	 * Launch the application.
	 * @param Tela principal da Aplicação.
	 */
	public static void main(String[] args) {
		try {
			JFramePrincipal window = new JFramePrincipal();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void preencherDados() {
		tableProduct.removeAll();
		for(Product p : pm.findAll()) {
			TableItem tableItem = new TableItem(tableProduct, SWT.NONE);
			tableItem.setText(new String[] {String.valueOf(p.getId()),
					p.getNome(), String.valueOf(p.getPreco()),
					String.valueOf(p.getQuantidade())});
		}		
	}
	
	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shlGestoDeProdutos.open();
		shlGestoDeProdutos.layout();
		while (!shlGestoDeProdutos.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		preencherDados();
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlGestoDeProdutos = new Shell();
		shlGestoDeProdutos.setText("Gestão de Produtos");
		shlGestoDeProdutos.setSize(470, 370);
		
		tableProduct = new Table(shlGestoDeProdutos, SWT.BORDER | SWT.FULL_SELECTION);
		tableProduct.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				TableItem[] selection = tableProduct.getSelection();
				int id = Integer.parseInt(selection[0].getText());
				Product p = pm.find(id);
				txtDescricao.setText(p.getDescricao());
				IdTxt.setText(String.valueOf(p.getId()));
				txtNome.setText(p.getNome());
				txtPreco.setText(String.valueOf(p.getPreco()));
				txtQuantidade.setText(String.valueOf(p.getQuantidade()));
			}
		});
		tableProduct.setBounds(29, 10, 393, 135);
		tableProduct.setHeaderVisible(true);
		tableProduct.setLinesVisible(true);
		
		TableColumn tblclmnId = new TableColumn(tableProduct, SWT.NONE);
		tblclmnId.setWidth(100);
		tblclmnId.setText("id");
		
		TableColumn tblclmnNewColumn = new TableColumn(tableProduct, SWT.NONE);
		tblclmnNewColumn.setWidth(100);
		tblclmnNewColumn.setText("Nome");
		
		TableColumn tblclmnNewColumn_1 = new TableColumn(tableProduct, SWT.NONE);
		tblclmnNewColumn_1.setWidth(100);
		tblclmnNewColumn_1.setText("Preço");
		
		TableColumn tblclmnQuantidade = new TableColumn(tableProduct, SWT.NONE);
		tblclmnQuantidade.setWidth(91);
		tblclmnQuantidade.setText("Quantidade");
		
		Label IdLbl = new Label(shlGestoDeProdutos, SWT.NONE);
		IdLbl.setBounds(10, 151, 31, 15);
		IdLbl.setText("id");
		
		IdTxt = new Text(shlGestoDeProdutos, SWT.BORDER);
		IdTxt.setBounds(101, 150, 237, 21);
		
		txtNome = new Text(shlGestoDeProdutos, SWT.BORDER);
		txtNome.setBounds(101, 177, 237, 21);
		
		Label lblNome = new Label(shlGestoDeProdutos, SWT.NONE);
		lblNome.setText("Nome");
		lblNome.setBounds(10, 183, 46, 15);
		
		Label lblPreco = new Label(shlGestoDeProdutos, SWT.NONE);
		lblPreco.setText("Preço");
		lblPreco.setBounds(10, 214, 46, 15);
		
		txtPreco = new Text(shlGestoDeProdutos, SWT.BORDER);
		txtPreco.setBounds(101, 208, 237, 21);
		
		Label lblQuantidade = new Label(shlGestoDeProdutos, SWT.NONE);
		lblQuantidade.setText("Quantidade");
		lblQuantidade.setBounds(10, 241, 66, 15);
		
		txtQuantidade = new Text(shlGestoDeProdutos, SWT.BORDER);
		txtQuantidade.setBounds(101, 238, 237, 21);
		
		Label lblDescricao = new Label(shlGestoDeProdutos, SWT.NONE);
		lblDescricao.setText("Descrição");
		lblDescricao.setBounds(10, 268, 66, 15);
		
		txtDescricao = new Text(shlGestoDeProdutos, SWT.BORDER);
		txtDescricao.setBounds(101, 265, 237, 21);
		
		Button btnSalvar = new Button(shlGestoDeProdutos, SWT.NONE);
		btnSalvar.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Product p = new Product();
				p.setDescricao(txtDescricao.getText());
				p.setNome(txtNome.getText());
				p.setPreco(Double.parseDouble(txtPreco.getText()));
				p.setQuantidade(Integer.parseInt(txtQuantidade.getText()));
				if(pm.create(p)) {
					JOptionPane.showMessageDialog(null, "Novo produto adicionado com sucesso!");
					preencherDados();
				} else JOptionPane.showMessageDialog(null, "Falha ao tentar adicionar novo produto!");
			}
		});
		btnSalvar.setBounds(101, 292, 75, 25);
		btnSalvar.setText("Salvar");
		
		Button btnDeletar = new Button(shlGestoDeProdutos, SWT.NONE);
		btnDeletar.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int result = JOptionPane.showConfirmDialog
				(null, "Tem certeza que quer excluir o produto?",
						"Confirmar", JOptionPane.YES_NO_OPTION);
				if(result == JOptionPane.YES_OPTION) {
					TableItem[] selection = tableProduct.getSelection();
					int id = Integer.parseInt(selection[0].getText());
					Product p = pm.find(id);
					pm.delete(p);
					preencherDados();
				}
			}
		});
		btnDeletar.setBounds(182, 292, 75, 25);
		btnDeletar.setText("Deletar");
		
		Button btnAlterar = new Button(shlGestoDeProdutos, SWT.NONE);
		btnAlterar.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Product p = new Product();
				p.setId(Integer.parseInt(IdTxt.getText()));
				p.setDescricao(txtDescricao.getText());
				p.setNome(txtNome.getText());
				p.setPreco(Double.parseDouble(txtPreco.getText()));
				p.setQuantidade(Integer.parseInt(txtQuantidade.getText()));
				if(pm.create(p)) {
					JOptionPane.showMessageDialog
					(null, "Alteração do produto realizada com sucesso!");
					preencherDados();
				} else JOptionPane.showMessageDialog
				(null, "Falha ao tentar alterar o produto!");
			}
		});
		btnAlterar.setBounds(263, 292, 75, 25);
		btnAlterar.setText("Alterar");
	}
}
