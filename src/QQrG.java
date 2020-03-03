import java.*;

public class QQrG extends Applet implements Runnable {
	/**
	 *@author Leandro Peres
	 *@category Game
	 *@GSS[DIA:1.9]
	 **/
	public void start() {
		enableEvents(AWTEvent.KEY_EVENT_MASK);
		new Thread(this).start();
	}
	
	public void run() {
		setSize(480, 260);
		
		BufferedImage tela = new BufferedImage(480,260,BufferedImage.TYPE_INT_RGB);
		Graphics2D g = tela.createGraphics();
		//g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		Graphics2D appletGraphics = (Graphics2D)getGraphics();

		BufferedImage iEspelhar = null;
		
		long Tempo = System.nanoTime();
		long processamento = 10000000L; //processamento

		int[][] level = new int[1][1];
		int[][] fallea = new int[1][1];
		int[][] caixas = new int[1][1];
		level[0][0] = -1;
		
		final int esY = 20;
		int dir, x, y, i, k, jogadorX, jogadorY, dirEsq, falle, dirX, dirY, EspelharX, comCaixa, nPintura;
		k = i = comCaixa = dir = jogadorX = jogadorY = dirEsq = dirX = dirY = falle = dir = nPintura = 0;
		EspelharX = -1;
		
		while (true) {
			long now = System.nanoTime();
			long delta = now - Tempo;
			processamento += delta;
			
			while (processamento >= 10000000L) {				
				processamento -= 10000000L;
				
				p[4] += 10;
				if (p[3] > 0) {
					p[3] -= 10;
					if (p[3] == 0) {
						nPintura = 0;
						for (y = 0; y < level.length; y++) {
							for (x = 0; x < level[0].length; ++x) {
								if ((EspelharX + x < level[0].length) && (level[y][EspelharX + x] >= 4) && (level[y][EspelharX + x] < 7)) {
									if ((EspelharX - x - 1 >= 0) && (EspelharX + x < level[0].length)) {
										if (level[y][EspelharX - 1 - x] == 7) {
											caixas[y][EspelharX - 1 - x] = level[y][EspelharX - 1 - x] = 7;
										} else {
											level[y][EspelharX - 1 - x] = level[y][EspelharX + x];
										}
									}
									if (level[y][EspelharX + x] != 7) {
										level[y][EspelharX + x] = caixas[y][EspelharX + x];
									}
								}
								if ((y == jogadorY) && (EspelharX + x == jogadorX)) {
									jogadorX = EspelharX - 1 - x;
									if ((jogadorX < 0) || (jogadorX >= level[0].length)) {
										p[4] = 0;
										p[2] = 2;
										jogadorX = 0;
									}
									if (EspelharX - 1 - x >= 0) {
										if (level[y][EspelharX - 1 - x] == 7) {
											p[4] = 0;
											p[2] = 2;
										}
										if (level[y][EspelharX - 1 - x] == 3) {
											p[4] = 0;
											p[2] = 1;
										}
									}
								}
							}	
						}
						if (p[2] <= 0) {
							if ((level[jogadorY][jogadorX] == 1) || (level[jogadorY][jogadorX] == 4) || (level[jogadorY][jogadorX] == 5)) {
								p[4] = 0;
								p[2] = 2;
							}
						}
						EspelharX = -1;
						p[1] -= 1;
					}
				} else if (level[0][0] == -1) {
					if (p[0] >= levels.length) {
						p[0] = 0;
					}
					if (p[0] < 0) {
						p[0] = levels.length - 1;
					}
					String l = levels[p[0]];
					caixas = new int[15][30];
					fallea = new int[15][30];
					level = new int[15][30];
					p[1] = Integer.valueOf(l.substring(0, 1));
					int cur = 1;
					for (y = 0; y < level.length; y++) {
						level[y][0] = 1;
						level[y][level[0].length - 1] = 1;
						for (x = 0; x < level[0].length - 2; ++x) {
							char c = l.charAt(cur);
							level[y][x + 1] = Integer.valueOf(c) - 48;
							cur += 1;
							if (level[y][x + 1] == 2) {
								jogadorX = x + 1;
								jogadorY = y;
								level[y][x + 1] = 0;
							}
							if (level[y][x+1] == 9) {
								for (x = 0; x < level[0].length - 2; ++x) {
									level[y][x + 1] = 0;
								}
								break;
							}
							if (level[y][x + 1] == 7) {
								caixas[y][x + 1] = 7;
							}
						}
					}
					nPintura = comCaixa = dirEsq = dirX = dirY = falle = dir = p[2] = p[3] = p[4] = 0;
					EspelharX = -1;
					pressed = new boolean[256];
				} else {
					if ((pressed[KeyEvent.VK_N]) || (pressed[KeyEvent.VK_P]) || (pressed[KeyEvent.VK_R])) {
						if (pressed[KeyEvent.VK_N])	p[0] += 1;
						if (pressed[KeyEvent.VK_P])	p[0] -= 1;
						level[0][0] = -1;
						processamento += 10000000L;
					} else if (p[2] >= 1) {
						if (p[4] < 256) {
							break;
						}
						boolean Suolv = false;
						for (i = 0; i < pressed.length; i++) {
							if (pressed[i]) {
								Suolv = true;
							}
						}
						if (Suolv) {
							level[0][0] = -1;
							if (p[2] == 1) {
								p[0] += 1;
							}
							processamento += 10000000L;
						}
					} else if ((EspelharX >= 0) && (p[3] <= 0)) {
						if (((pressed[KeyEvent.VK_ESCAPE]) || (pressed[KeyEvent.VK_CONTROL])) && (EspelharX >= 0)) {
							pressed[KeyEvent.VK_ESCAPE] = pressed[KeyEvent.VK_CONTROL] = false;
							EspelharX = -1;
						}
						if (((pressed[KeyEvent.VK_SPACE]) || (pressed[KeyEvent.VK_SHIFT])) && (p[1] > 0)) {
							pressed[KeyEvent.VK_SHIFT] = pressed[KeyEvent.VK_SPACE] = false;
							p[3] = 960;
						}
						if (pressed[KeyEvent.VK_LEFT]) {
							pressed[KeyEvent.VK_LEFT] = false;
							if (EspelharX > 1) {
								EspelharX -= 1;
								k = 1;
							}
						}
						if (pressed[KeyEvent.VK_RIGHT]) {
							pressed[KeyEvent.VK_RIGHT] = false;
							if (EspelharX < 28) {
								EspelharX += 1;
								k = 1;
							}
						}
						if ((EspelharX >= 0) && (k != 0)) {
							k = 0;
							nPintura = 1;
						}
					} else if ((dirX == 0) && (dirY == 0) && (falle == 0)) {
						for (x = 0; x < level[0].length; ++x) {
							int dirfalle = 0;
							int dirfallejogador = 0;
							for (y = level.length - 1; y >= 0; y--) {
								if ((level[y][x] == 1) || (level[y][x] == 5)) {
									dirfallejogador = dirfalle = 0;
								}
								if (((level[y][x] == 0) || (level[y][x] > 5)) && ((jogadorX != x) || (jogadorY != y))) {
									dirfalle += 1;
									dirfallejogador += 1;
								}
								if (level[y][x] == 3) {
									dirfallejogador += 1;
								}
								if ((x == jogadorX) && (y == jogadorY - comCaixa) && (comCaixa > 0)) {
									dirfallejogador = dirfalle = 0;
								}
								if (((level[y][x] == 4) || ((x == jogadorX) && (y == jogadorY))) && (dirfallejogador > 0)) {
									if ((x == jogadorX) && (y == jogadorY)) {
										dirfalle = dirfallejogador;
										jogadorY += dirfalle;
									} else {
										level[y + dirfalle][x] = level[y][x];
										level[y][x] = caixas[y][x];	
									}
									fallea[y + dirfalle][x] = dirfalle * 16;
									falle = 1;
									if ((x == jogadorX) && (y == jogadorY - dirfalle)) {
										y -= comCaixa;
									}
								}

							}
						}
						if (falle != 0) {
						} else {
							dirEsq = 0;
							if (((pressed[KeyEvent.VK_SPACE]) || (pressed[KeyEvent.VK_SHIFT])) && (p[1] > 0)) {
								pressed[KeyEvent.VK_SPACE] = pressed[KeyEvent.VK_SHIFT] = false;
								if (EspelharX < 0) {
									EspelharX = 15;
									p[3] = 0;
									processamento += 10000000L;
									k = 1;
								}
							}
							if ((pressed[KeyEvent.VK_LEFT]) && (p[4] > 200)) {
								dirEsq = -1;
								if (dir == 0) {
									dir = 1;
									p[4] = 0;
									break;
								}
								dir = 1;
							}
							if ((pressed[KeyEvent.VK_RIGHT]) && (p[4] > 200)) {
								dirEsq = 1;
								if (dir == 1) {
									dir = 0;
									p[4] = 0;
									break;
								}
								dir = 0;
							}
							if (pressed[KeyEvent.VK_LEFT] && pressed[KeyEvent.VK_RIGHT]){
								pressed[KeyEvent.VK_RIGHT] = pressed[KeyEvent.VK_LEFT] = false;
						
							}
							if (((pressed[KeyEvent.VK_UP]) || (pressed[KeyEvent.VK_DOWN])) && (comCaixa == 0)) {
								pressed[KeyEvent.VK_UP] = pressed[KeyEvent.VK_DOWN] = false;
								dirEsq = 1;
								if (dir == 1) {
									dirEsq = -1;
								}
								if ((jogadorX + dirEsq >= 0) && (jogadorX + dirEsq < level[jogadorY].length)) {
									if (level[jogadorY][jogadorX + dirEsq] == 4) {
										if ((jogadorY - 1 >= 0) && (((level[jogadorY - 1][jogadorX + dirEsq] == 0) || (level[jogadorY - 1][jogadorX + dirEsq] > 5) || (level[jogadorY - 1][jogadorX + dirEsq] == 3)))) {
											comCaixa = 1;
											level[jogadorY][jogadorX + dirEsq] = caixas[jogadorY][jogadorX + dirEsq];
										}
									}
								}
								break;
							}
							if (((pressed[KeyEvent.VK_DOWN]) || (pressed[KeyEvent.VK_UP])) && (comCaixa != 0)) {
								pressed[KeyEvent.VK_UP] = pressed[KeyEvent.VK_DOWN] = false;
								dirEsq = 1;
								if (dir == 1) {
									dirEsq = -1;
								}
								if ((jogadorX + dirEsq >= 0) && (jogadorX + dirEsq < level[jogadorY].length)) {
									if (((level[jogadorY][jogadorX + dirEsq] == 0) || (level[jogadorY][jogadorX + dirEsq] > 5)) && ((level[jogadorY - 1][jogadorX + dirEsq] == 0) || (level[jogadorY - 1][jogadorX + dirEsq] > 5))) {
										level[jogadorY][jogadorX + dirEsq] = 4;
										comCaixa = 0;
									} else if ((level[jogadorY - 1][jogadorX + dirEsq] == 0) || (level[jogadorY - 1][jogadorX + dirEsq] > 5)) {
										level[jogadorY - 1][jogadorX + dirEsq] = 4;
										comCaixa = 0;
									}
								}
								break;
							}
							if (dirEsq != 0) {
								if ((jogadorX + dirEsq >= 0) && (jogadorX + dirEsq < level[jogadorY].length)) {
									if ((comCaixa == 0) || (level[jogadorY-comCaixa][jogadorX + dirEsq] == 0) || (level[jogadorY-comCaixa][jogadorX + dirEsq] > 5) || (level[jogadorY-comCaixa][jogadorX + dirEsq] == 3)) {
										if ((level[jogadorY][jogadorX + dirEsq] == 0) || (level[jogadorY][jogadorX + dirEsq] > 5) || (level[jogadorY][jogadorX + dirEsq] == 3)) {
											jogadorX += dirEsq;
											dirX = -dirEsq * 16;
											dirY = 0;
										} else if ((jogadorY - 1 >= 0) && (((level[jogadorY - 1][jogadorX + dirEsq] == 0) || (level[jogadorY - 1][jogadorX + dirEsq] > 5) || (level[jogadorY - 1][jogadorX + dirEsq] == 3)))) {
											if ((comCaixa == 0) || (level[jogadorY-comCaixa - 1][jogadorX] == 0) || (level[jogadorY-comCaixa - 1][jogadorX] > 5) || (level[jogadorY-comCaixa - 1][jogadorX] == 3)) {
												if ((comCaixa == 0) || (level[jogadorY-comCaixa - 1][jogadorX + dirEsq] == 0) || (level[jogadorY-comCaixa - 1][jogadorX + dirEsq] > 5) || (level[jogadorY-comCaixa - 1][jogadorX + dirEsq] == 3)) {
													jogadorX += dirEsq;
													jogadorY -= 1;
													dirX = -dirEsq * 16;
													dirY = 16;
												}
											}
										}
									}
								}
							}
						}
					} else if (falle != 0) {
						falle = 0;
						for (y = level.length - 1; y >= 0; y--) {
							for (x = 0; x < level[0].length; ++x) {
								if (fallea[y][x] > 0) {
									fallea[y][x] -= 1;
									falle = 1;
									if ((fallea[y][x] == 0) && (x == jogadorX) && (y + 1 == jogadorY)) {
										p[2] = 2;	
									}
								}
							}
						}
						if (falle == 0) {
							for (x = 0; x < level[0].length; ++x) {
								if (level[level.length - 1][x] == 4) {
									level[level.length - 1][x] = 0;
								}
								if (jogadorY == level.length - 1) {
									p[2] = 2;
								}
							}
							if (level[jogadorY][jogadorX] == 3) {
								p[2] = 1;
							}
						}
					} else {
						if (dirY > 0) {
							dirY -= 4;
						} else if (dirX > 0) {
							dirX -= 1;
						} else if (dirX < 0) {
							dirX += 1;
						} else if (dirY < 0) {
							dirY += 4;
						}
						if ((dirY == 0) && (dirX == 0)) {
							if (level[jogadorY][jogadorX] == 3) {
								p[2] = 1;
							}
							processamento += 10000000L;
						}
					}
				}
			}

			Tempo = now;

			g.setColor(Color.WHITE);
			g.fillRect(0, 0, 480, 300);

			int dirPX = jogadorX * 16 + dirX;
			int dirPY = jogadorY * 16 + esY + dirY - fallea[jogadorY][jogadorX];
			g.setColor(Color.BLACK); //Personagem
			g.fillRect(dirPX + 5, dirPY + 1, 6, 11);
			g.fillRect(dirPX + 11 - dir * 8, dirPY + 5, 2, 2);
			g.fillRect(dirPX + 5, dirPY + 12, 2, 5);
			g.fillRect(dirPX + 9, dirPY + 12, 2, 5);
			if (comCaixa != 0) {
				dirPY -= 19;
				g.fillRect(dirPX + 10, dirPY + 18, 2, 2);
				g.fillRect(dirPX + 10, dirPY + 17, 2, 2);
				g.fillRect(dirPX + 5, dirPY + 18, 2, 2);
				g.fillRect(dirPX + 5, dirPY + 17, 2, 2);
				g.setColor(Color.BLACK);
				g.fillRect(dirPX, dirPY + 2, 16, 12);
				g.fillRect(dirPX + 2, dirPY, 12, 16);
				g.setColor(Color.WHITE);
				g.fillRect(dirPX + 3, dirPY + 3, 10, 10);
				g.setColor(Color.BLACK);
				g.fillRect(dirPX + 5, dirPY + 5, 6, 6);
			}
			
			for (y = 0; y < level.length; y++) {
				for (x = 0; x < level[0].length; ++x) {
					g.setColor(Color.BLACK);

					dirPX = x * 16;
					dirPY = y * 16 + esY - fallea[y][x];
					if (caixas[y][x] == 7) {
						g.drawLine(dirPX + 4, dirPY + 4, dirPX + 12, dirPY + 12);
						g.drawLine(dirPX + 12, dirPY + 4, dirPX + 4, dirPY + 12);
					}
					if (level[y][x] != 0) { //Porta
						if (level[y][x] == 3) {
							g.drawRect(dirPX - 5, dirPY , 10, 20);
							g.drawRect(dirPX - 5, dirPY , 11, 21);
							g.drawRect(dirPX + 1, dirPY + 9 , 2, 2);
						}
						if ((level[y][x] == 1) || (level[y][x] == 4) || (level[y][x] == 5)) {
							if ((level[y][x] > 1) && (p[3] > 0) && (EspelharX - 1 < x)) continue;
							g.fillRect(dirPX, dirPY + 2, 16, 12);
							g.fillRect(dirPX + 2, dirPY, 12, 16);
							if (level[y][x] == 1) {
								g.setColor(Color.WHITE);
								g.fillRect(dirPX + 6, dirPY + 6, 4, 4);
							}
							if (level[y][x] == 4) {
								g.setColor(Color.WHITE);
								g.fillRect(dirPX + 3, dirPY + 3, 10, 10);
								g.setColor(Color.BLACK);
								g.fillRect(dirPX + 5, dirPY + 5, 6, 6);
							}
						}
						if ((level[y][x] == 6) && (p[3] <= 0)) {
							g.fillRect(dirPX + 6, dirPY + 6, 4, 4);
						}
					}
				}
			}
	
			if (EspelharX >= 0) {
				for (i = 0; i < 2; i++) {

					if (i == 0) {
						if (nPintura > 0) {
							nPintura = 0;
							iEspelhar = new BufferedImage(480-16 * EspelharX, 240, BufferedImage.TYPE_INT_ARGB_PRE);
							g = iEspelhar.createGraphics();
							g.setColor(Color.BLACK);
							for (y = 0; y < level.length; y++) {
								for (x = EspelharX; x < level[y].length; ++x) {
									g.setColor(Color.BLACK); //
									dirPX = iEspelhar.getWidth() - (x - EspelharX + 1) * 16;
									dirPY = y * 16;
									if ((y == jogadorY) && (x == jogadorX)) {
										g.fillRect(dirPX + 5, dirPY + 1, 6, 11);
										g.fillRect(dirPX + 11 - dir * 8, dirPY + 5, 2, 2);
										g.fillRect(dirPX + 5, dirPY + 12, 2, 5);
										g.fillRect(dirPX + 9, dirPY + 12, 2, 5);
									}
									if (level[y][x] > 1) {			
										
										if ((level[y][x] == 4) || (level[y][x] == 5)) {
											g.fillRect(dirPX, dirPY + 2, 16, 12);
											g.fillRect(dirPX + 2, dirPY, 12, 16);
											if (level[y][x] == 4) {
												g.setColor(Color.WHITE);
												g.fillRect(dirPX + 3, dirPY + 3, 10, 10);
												g.setColor(Color.BLACK);
												g.fillRect(dirPX + 5, dirPY + 5, 6, 6);
											}
										}
										if (level[y][x] == 6) {
											g.fillRect(dirPX + 6, dirPY + 6, 4, 4);
										}
									}
								}
							}
							g = tela.createGraphics();
						}
					} else {
						g.setColor(Color.BLACK); //Espelhar
						AlphaComposite compo = (AlphaComposite)(g.getComposite());
						for (y = 0; y < level.length; y += 2) {
							g.fillRect(EspelharX * 16 - 1, y * 16 + esY, 3, 16);
						}
						g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)(0.6f)));
						g.drawImage(iEspelhar, EspelharX * 16 - iEspelhar.getWidth(), esY, null);
						g.setComposite(compo);
					}
				}
			}
			g.setColor(Color.BLACK); //Fonte
			if (p[3] > 0) {
				g.drawImage(iEspelhar, EspelharX * 16 + (int)((float)(iEspelhar.getWidth()) * ((p[3]) - 480f) / 480f), esY,
						EspelharX * 16, esY + 240, 0, 0, iEspelhar.getWidth(), iEspelhar.getHeight(), null);
			}
			String s = "";
			g.setFont(g.getFont().deriveFont(15f).deriveFont(1));
			if (p[2] > 0) {
				g.setColor(Color.WHITE); //Fundo Diálogo
				g.fillRect(100, esY + 115, 280, 60);
				g.setColor(Color.BLACK); //Fonte Diálogo
				g.drawRect(100, esY + 115, 280, 60);
				s = "Parabéns!";
				if (p[2] > 1) {
					s = "Por favor, tente novamente!";
				}
				g.drawString(s, 240 - g.getFontMetrics().stringWidth(s)/2, esY + 120 + 20);
				
				s = "Avançar de nível.";
				if (p[2] > 1) {
					s = "Reiniciar nível";
				}
				g.drawString(s, 240 - g.getFontMetrics().stringWidth(s)/2, esY + 120 + 40);
				//g.drawString(String.valueOf(p[0] + 1) + " / " + levels.length, 240 - g.getFontMetrics().stringWidth(s)/2, esY + 120 + 60);
			} else {
				if (EspelharX >= 0) {
					if (p[0] == 1) {
						s = fraso[0];
						g.drawString(s, 240 - g.getFontMetrics().stringWidth(s)/2, esY + 20);
						s = fraso[1];
						g.drawString(s, 240 - g.getFontMetrics().stringWidth(s)/2, esY + 35);
						s = fraso[2];
						g.drawString(s, 240 - g.getFontMetrics().stringWidth(s)/2, esY + 50);
					}
				} else {
					s = fraso[0];
					if (p [0] == 0) {
					s = "Mova o 'Perez' com as teclas de cursor";
					}
					if (p [0] == 1) {
					s = "pegar uma caixa com acima e soltá-lo com para baixo";
					}
					if ((p [0] == 2) && (p [1] > 0)) {
					s = "Pressione barra de espaço ou shift para abrir o modo de espelho";
					}
					if (p [0] == 3) {
					s = "quantas vezes você pode usar o modo de espelho";
					g. drawString (s, 240 - g.getFontMetrics() .stringWidth(s) / 2, esY + 35);
					s = "No canto superior esquerdo você pode ver";
					}
					if (p [0] == 14) {
					s = "ar destrói tudo após espelhamento.";
					}
					if (p [0] == 11) {
					s = "o x não aceita coisas novas depois de espelhamento.";
					}
					g. drawString (s, 240 - g.getFontMetrics() .stringWidth(s) / 2, esY + 20);
					}

			}
			
			
			s = "Nível: " + String.valueOf(p[0] + 1) + " / " + levels.length;;
			g.drawString(s, 475 - g.getFontMetrics().stringWidth(s), esY - 4);
			s = beta[0] + " - " + beta[2] + beta[3] + beta[4];
			g.drawString(s, 240 - g.getFontMetrics().stringWidth(s)/2, esY - 4);
			g.setColor(Color.GRAY);
			g.drawString(beta[5], 240 - g.getFontMetrics().stringWidth(s)/4, esY + 8);
			g.setColor(Color.BLACK);
			g.drawString("Espelhar: " + String.valueOf(p[1]), 5, esY - 4);
			
			appletGraphics.drawImage(tela, 0, 0, null);

			try {
			Thread.sleep(5);
			} catch (Exception e) {
				System.out.println("Erro[" + e.getMessage() + "]");
			};

			if (!isActive()) {
				return;
			}
		}
	}
	
	@Override
    public void processEvent(AWTEvent e) {
		if (e.getID() == KeyEvent.KEY_PRESSED) {
			KeyEvent event = (KeyEvent) e;
			if (event.getKeyCode() < 256) {
				pressed[event.getKeyCode()] = true;
			}
		}
		if (e.getID() == KeyEvent.KEY_RELEASED) {
			KeyEvent event = (KeyEvent) e;
			if (event.getKeyCode() < 256) {
				pressed[event.getKeyCode()] = false;
			}
		}
	}
	
	/**
	 * @Sohne[DIA:1.8];
	 * @DIA[Declarations];
	 **/
	
	/**
	* primeiro valor = Contador de Espelhar
	* 0 = Nada
	* 1 = Parede
	* 2 = Jogador
	* 3 = Objetivo
	* 4 = Caixa
	* 5 = Espelho da parede
	* 6 = Ar
	* 7 = Não pode espelhar
	* 9 = Linha completa vazia
	* 
	* 
	* p[0] == Level Atual
	* p[1] == Contador de Espelhar
	* p[2] == aktuelles Level geschafft klick
	* p[3] == Espelhar time
	* p[4] == Time
	*/
	
	private final String[] levels = new String[] {
		
		"0"+
		"9"+
		"9"+
		"9"+
		"9"+
		"9"+
		"9"+
		"9"+
		"9"+
		"9"+
		"9"+
		"9"+
		"0000000000000100000000000000"+
		"0000000000010100000000000000"+
		"2000004000111100000000000030"+
		"1111111111111111111111111111",
		
		"1"+
		"9"+
		"9"+
		"9"+
		"9"+
		"9"+
		"9"+
		"9"+
		"9"+
		"9"+
		"9"+
		"9"+
		"0000000000000000000000000000"+
		"0000000000000000000000000000"+
		"0200000000000005500000000030"+
		"1111111111100111111111111111",
		
		"1"+
		"9"+
		"9"+
		"9"+
		"9"+
		"9"+
		"9"+
		"9"+
		"9"+
		"9"+
		"9"+
		"9"+
		"0000000000001000000000000000"+
		"0000000000001000000000500000"+
		"0200000000101000000000500030"+
		"1111111111111111111111111111",
		
		"09"+
		"9"+
		"9"+
		"9"+
		"9"+
		"9"+
		"9"+
		"9"+
		"9"+
		"9"+
		"9"+
		"0000000000000100000000000000"+
		"0000000400000100000000000000"+
		"0200004400000100000000000030"+
		"1111111111111111111111111111",
		
		"19"+
		"9"+
		"9"+
		"9"+
		"9"+
		"9"+
		"9"+
		"9"+
		"9"+
		"0000000000005500000000000000"+
		"0000000000005500000000000000"+
		"0000000000005500000000000000"+
		"0000000000005500000000000000"+
		"0200000000005500000000000030"+
		"1111111111111111111111111111",
		
		
		"09"+
		"9"+
		"9"+
		"9"+
		"9"+
		"9"+
		"9"+
		"9"+
		"9"+
		"0000000000000001000000000000"+
		"0000000000000000000000000000"+
		"0000000000000011010000000000"+
		"0000000400000000010000000000"+
		"0200004440000000110000000030"+
		"1111111111111111111111111111",
		
		"29"+
		"9"+
		"9"+
		"9"+
		"9"+
		"9"+
		"9"+
		"9"+
		"9"+
		"9"+
		"0000000001100000000000000000"+
		"0000000001110000000000000000"+
		"0000000001155000000000000000"+
		"0200000001155500000000004030"+
		"1111111111111111111111111111",
		
		"29"+
		"9"+
		"9"+
		"9"+
		"9"+
		"9"+
		"9"+
		"9"+
		"9"+
		"9"+
		"0000000001100000000000000000"+
		"0000000001100500000000000000"+
		"0000000001100500050000000000"+
		"0200000001100505050000000030"+
		"1111111111111111111111111111",
		
		"49"+
		"9"+
		"9"+
		"9"+
		"9"+
		"9"+
		"9"+
		"9"+
		"9"+
		"9"+
		"9"+
		"0000000000000000000000000000"+
		"0000000000010000100100500000"+
		"0200000000010000100100500030"+
		"1111111111111111111111111111",
		
		"19"+
		"9"+
		"9"+
		"9"+
		"9"+
		"9"+
		"9"+
		"9"+
		"9"+
		"9"+
		"0000000000001000000000000000"+
		"0000000000771040000000000000"+
		"0000000000111010000000000000"+
		"0200000001111000000000000030"+
		"1111111111111111111111111111",
		
		"29"+
		"9"+
		"9"+
		"0000000000000400000000000000"+
		"0000001000000100000000000000"+
		"9"+
		"9"+
		"9"+
		"9"+
		"9"+
		"9"+
		"9"+
		"0000000000001010000000000000"+
		"0200000000011211000000000030"+
		"1111111111111111111111111111",
		
		"19"+
		"9"+
		"9"+
		"9"+
		"9"+
		"9"+
		"9"+
		"9"+
		"9"+
		"9"+
		"0000000000000000100000000000"+
		"0000000000000777100000040000"+
		"0000000000001777100500040000"+
		"0200040000011100105500055030"+
		"1111111111111111111111111111",
		
		"19"+
		"9"+
		"9"+
		"9"+
		"9"+
		"9"+
		"9"+
		"9"+
		"9"+
		"9"+
		"9"+
		"9"+
		"0000000000011660000000000000"+
		"0200000000011660000000000030"+
		"1111111111111111111111111111",
		
		"29"+
		"9"+
		"9"+
		"9"+
		"9"+
		"9"+
		"9"+
		"9"+
		"9"+
		"9"+
		"0200000000000110000000000000"+
		"5550555000000110010000000000"+
		"7777777777000115600000000000"+
		"7777777777004155500000000030"+
		"1111111111111111111111111111",
		
		"19"+
		"9"+
		"9"+
		"9"+
		"9"+
		"9"+
		"9"+
		"9"+
		"9"+
		"9"+
		"0000000000000500000000000000"+
		"0000000001100500000000000000"+
		"0000000001150500000000000000"+
		"0200000041155500000000000030"+
		"1111111111111111111111111111",
		
		"19"+
		"9"+
		"9"+
		"9"+
		"9"+
		"9"+
		"9"+
		"9"+
		"9"+
		"0000000000003000000000000000"+
		"0000000000001000000000000000"+
		"0000001000111000000000000020"+
		"0000001047111016000000000111"+
		"0000000040155555400000001111"+
		"1111111111111011111111111111",
		
		"29"+
		"9"+
		"9"+
		"9"+
		"9"+
		"9"+
		"9"+
		"9"+
		"9"+
		"0000000000000040000000000000"+
		"0000000000000040000000000000"+
		"0000000000001110060000000000"+
		"0000000000001110060000000000"+
		"0200000000001110000000000030"+
		"1111111111111111111111111111",
		
		"19"+
		"9"+
		"9"+
		"0000000444000000000000000000"+
		"0000000111666000000000000000"+
		"9"+
		"9"+
		"9"+
		"9"+
		"0000000000000500000000000000"+
		"0000000000000500000000000000"+
		"0000001000000000100000000030"+
		"0000011000110000110000000111"+
		"0204111100511000111000001111"+
		"1111111110111111111111111111",
		
		"19"+
		"9"+
		"9"+
		"9"+
		"9"+
		"9"+
		"0000004000000000000000000000"+
		"0000044000000000000000000000"+
		"0006611111111666611111166000"+
		"0006611111111666611111166000"+
		"0000000111111661166110000000"+
		"0000000111111661166110000000"+
		"0000000666611111166110000000"+
		"0200000666611111166110000030"+
		"1111111111111111111111111111",
		
		"19"+
		"9"+
		"9"+
		"9"+
		"9"+
		"9"+
		"9"+
		"9"+
		"9"+
		"0000010000000400000000000000"+
		"0000010000000400000000000000"+
		"0000000000040100000000000030"+
		"0000000110016100000000000111"+
		"0200001000000150000000001111"+
		"1111111111111111111111111111",
		
		"29"+
		"9"+
		"9"+
		"9"+
		"9"+
		"9"+
		"9"+
		"9"+
		"9"+
		"9"+
		"0000000000050505600000000000"+
		"0000000001050505606000000030"+
		"0000000001050565006000000111"+
		"0200000001050505000000001111"+
		"1111111111111111111111111111",
		
		"19"+
		"9"+
		"9"+
		"9"+
		"9"+
		"9"+
		"9"+
		"9"+
		"9"+
		"9"+
		"9"+
		"0000000000000004000000000300"+
		"0000000000001661100000001110"+
		"0200000400001661000000001110"+
		"1111111111111111611111111111",
		
		"29"+
		"9"+
		"9"+
		"9"+
		"9"+
		"9"+
		"9"+
		"9"+
		"9"+
		"9"+
		"9"+
		"0000000000000010000400000030"+
		"0000000000100010500100000111"+
		"0200000401100010500100001111"+
		"1111111111111111111111111111",
		
		"29"+
		"9"+
		"9"+
		"9"+
		"9"+
		"9"+
		"9"+
		"9"+
		"9"+
		"9"+
		"9"+
		"0000000000100100000006040000"+
		"0000000010000105006001010000"+
		"0200040110000105006001010030"+
		"1111111111111111111111111111",
		
		"29"+
		"9"+
		"9"+
		"9"+
		"9"+
		"9"+
		"9"+
		"9"+
		"9"+
		"9"+
		"9"+
		"0300000000000000000000000000"+
		"1117777000000000000000000000"+
		"1117777000000000240000000000"+
		"1111111111111001111661111111",
		
		"09" +
		"9" +
		"9" +
		"9" +
		"9" +
		"9" +
		"0301110000000000000000001000" +
		"1111010000000000000000441000" +
		"0000014400000000100400111000" +
		"0000014440000000124440100000" +
		"0000014444000000111110100000" +
		"0000011111000011100011100000" +
		"0000000001000410000000000000" +
		"0000000001101110000000000000" +
		"0000000000111000000000000000",
		
		"19"+
		"9"+
		"9"+
		"0000000000000000100000000000"+
		"0000000000000000000000000000"+
		"0000000000000000100000000000"+
		"0000004000010000100000000000"+
		"0000044400001111111000000000"+
		"0000111111000000001000000000"+
		"0000000000100000001010000000"+
		"0000000000010000000010000000"+
		"0000000000000000000111000000"+
		"0010000010004410101111100000"+
		"0200004400404416111111110030"+
		"1111111111111111111111111111",
		
		"19"+
		"9"+
		"0000000000000000020000000000"+
		"0000000001000001111101000000"+
		"0000000001440000000001000000"+
		"0000000001111100000001000000"+
		"0000000000000144400001000000"+
		"0000000000000111111001000000"+
		"9"+
		"0000000000000000030000000000"+
		"0000000000100000110000000000"+
		"0000000000000000110000000000"+
		"0000000001000000710000000000"+
		"0000000011111111100111000000"+
		"9",
		
		"29"+
		"0000000000030000000000000000"+
		"0000000000111000000000000000"+
		"0000000000000110000000000000"+
		"0000000000000000000000000000"+
		"0000000000100000100000000000"+
		"0000004440104400000000000000"+
		"0000011111111111111110000000"+
		"0000000000000010000000000000"+
		"0000000000000000000000001000"+
		"0000000000000000000001110000"+
		"0000000000000000400050000000"+
		"0000004000000000501110000000"+
		"0000004440200001115000000000"+
		"0000001111110000010000000000",
};
	private final int[] p = new int[5];
	private boolean[] pressed = new boolean[256];
	private String[] beta = {"Perez", "Game", "V.:", "1.8", "[ADMIN BETA]", "Leandro Peres"};
	private String[] fraso = {"Mova a linha de espelho com as teclas do cursor", "Pressione espaço para movimentar as peças do espelho", "Pressione ESC ou CTRL para sair do modo de espelho","Pressione 'r' para reiniciar o nível, caso seja necesário", ""};
}