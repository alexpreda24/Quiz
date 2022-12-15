package com.example.project;

import java.io.*;
import java.util.*;
import java.io.IOException;
import java.io.FileWriter;
import java.io.FileNotFoundException;
public class Tema1 {
	static int indexforAnswer = 0;
	static int indexforSubmitedQuizz = 0;
	public static Quizz findQuizbyId(Set<Quizz> quizz,int id) {
		for (Quizz q : quizz) {
			if (q.getIDquiz() == id) {
				return q;
			}
		}
		return null;
	}
	public static boolean quizzExists(Set<Quizz> quizz,int ID) {
		for(Quizz q:quizz) {
			if(q.getIDquiz()==ID) {
				return true;
			}
		}
		return false;
	}
	public static  Quizz quizzPosition(Set<Quizz> quizz,int ID) {
		for(Quizz q:quizz) {
			if(q.getIDquiz()==ID) {
				return q;
			}
		}
		return null;
	}
	public static Question findQuestionByID(Set<Question> questions,int id) {
		for (Question q : questions) {
			if (q.getIDforQuestion() == id) {
				return q;
			}
		}
		return null;
	}
	public static boolean userExists(Set<User> users, User user) {
		for (User u : users)
			if (u.getName().equals(user.getName()) && u.getPassword().equals(user.getPassword()))
				return true;
		return false;
	}

	public static boolean questionExist(Set<Question> questions, String question) {
		for (Question q : questions)
			if (q.getName().equals(question))
				return true;
		return false;
	}
	public static boolean IDExist(Set<Question> questions, int ID) {
		for (Question q : questions)
			if (q.getIDforQuestion() == ID)
				return true;
		return false;
	}

	public static boolean  QuizzExist(Set<Quizz> quizz, String quizzi) {
		for (Quizz q : quizz)
			if (q.getName().equals(quizzi))
				return true;
		return false;
	}

	public static boolean AnswerExist(Set<Answer> answers, int ID) {
		for (Answer a : answers)
			if (a.getId() == ID)
				return true;
		return false;
	}
	public static Answer AnswerPosition(Set<Answer> answers, int ID) {
		for (Answer a : answers) {
			boolean x = a.getId() == ID;
			if (x) {
				return a;
			}
		}
		return null;
	}
    public static Question QuestionAnswer(Set<Question> questions, Answer ans) {
			for (Question q : questions)
			for(Answer a: q.answersss) {
				if (a.getId() == ans.getId()) {
					return q;
				}
			}
		return null;
	}

	public static void main(final String[] args) {

		if (args == null) {
			System.out.println("Hello world!");
		} else {
			String file = "myfile.csv";
			Set<User> users = new LinkedHashSet<>();
			Set<Question> questions = new LinkedHashSet<>();
			Set<Quizz> quizz = new LinkedHashSet<>();
			Set<Answer> ans = new HashSet<>();

			if (args[0].equals("-cleanup-all")) {
				Question.setID(-1);
				Quizz.setIDquizz(-1);
				Answer.setID(-1);
				try (PrintWriter wr = new PrintWriter(file)) {
					wr.print("");
					wr.flush();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
			if (args[0].equals("-create-user")) {
				try {
					BufferedReader br = new BufferedReader(new FileReader((file)));
					String line;

					while ((line = br.readLine()) != null) {
						String[] parols = line.split(",");
						if (parols.length >= 2) {
							User u = new User(parols[0], parols[1]);
							users.add(u);
						}
						// process the line
					}
				} catch (IOException e) {
					e.printStackTrace();
				}

				if (args.length == 1 || (!args[1].contains("-u"))) {

					System.out.print("{'status' : 'error', " +
							"'message' : 'Please provide username'}");
				} else {
					String[] args1 = args[1].split("'");
//					System.out.println(args[2]);

					if (args.length == 2 || (!args[2].contains("-p")))
						System.out.print("{'status' : 'error', " +
								"'message' : 'Please provide password'}");
					else  {
						String[] args2 = args[2].split("'");
						int ok = 1;
						for (User u1 : users)
							if (u1.getName().equals(args1[1])) {
								System.out.println("{'status' : 'error', " +
										"'message' : 'User already exists'}");
								ok = 0;
							}
						if (ok == 1) {
							User u = new User(args1[1], args2[1]);
							try (FileWriter fw = new FileWriter("myfile.csv", true);
								 BufferedWriter bw = new BufferedWriter(fw);
								 PrintWriter out = new PrintWriter(bw)) {
								out.print(u.getName());
								out.print(",");
								out.print(u.getPassword());
								out.println();
								System.out.print("{'status' : 'ok', 'message' : 'User created successfully'}");


							} catch (IOException e) {
								//exception handling left as an exercise for the reader
								e.printStackTrace();
							}
						}
					}
				}
			}
			//sort a vector
			if (args[0].equals("-create-question")) {
				try {
					BufferedReader br = new BufferedReader(new FileReader((file)));
					String line;
					while ((line = br.readLine()) != null) {

						String[] parols = line.split(",");

						if (parols.length >= 2) {
							User u = new User(parols[0], parols[1]);
							users.add(u);
							if (parols.length > 4) {
								int ID = Integer.parseInt(parols[4]);
								Question q = new Question(u, parols[2], parols[3],ID);
								questions.add(q);
							}
						}

					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				if (args.length == 1 || (!args[1].contains("-u")) ||
						!(args.length >= 3 && args[2].contains("-p")))
					System.out.println("{'status' : 'error', 'message' : " +
							"'You need to be authenticated'}");
				else {
					String[] args1 = args[1].split("'");
					String[] args2 = args[2].split("'");
					User u = new User(args1[1], args2[1]);
					if (!userExists(users, u)) {
						System.out.println("{'status' : 'error', 'message' : " +
								"'Login failed'}");

					} else if (args.length >= 4 && !args[3].contains("-text")) {
//						String[] args3 = args[3].split("'");
						System.out.println("{'status' : 'error', 'message' : " +
								"'No question text provided'}");
					} else if (args.length <= 5) {
						System.out.println("{'status' : 'error', 'message' : " +
								"'No answer provided'}");
					} else if (args.length <= 7) {
						System.out.println("{'status' : 'error', 'message' : " +
								"'Only one answer provided'}");
					} else if (args.length >= 16) {
						System.out.println("{'status' : 'error', 'message' : " +
								"'More than 5 answers were submitted'}");
					} else {
						int ok = 1;
						String[] args5 = args[5].split("'");
						args5[0] = args5[0].replace(" ", "");
							if (!args5[0].equals("-answer-1")) {
								System.out.println("{'status' : 'error', 'message' : " +
										"'Answer 1 has no answer description'}");
								ok = 0;
							}
							String[] args6 = args[6].split(" ");
							args6[0] = args6[0].replace(" ", "");
							if (ok == 1 && !args6[0].equals("-answer-1-is-correct")) {
								System.out.println("{'status' : 'error', 'message' : " +
										"'Answer 1 has no answer correct flag'}");
								ok = 0;
							}

						if (ok == 1) {
							String[] args7 = args[7].split("'");
							args7[0] = args7[0].replace(" ", "");
							if (!args7[0].equals("-answer-2")) {
								System.out.println("{'status' : 'error', 'message' : " +
										"'Answer 2 has no answer description'}");
								ok = 0;
							}
							if (ok == 1) {
								String[] args8 = args[8].split("'");
								args8[0] = args8[0].replace(" ", "");
								if (!args8[0].equals("-answer-2-is-correct")) {
									System.out.println("{'status' : 'error', 'message' : " +
											"'Answer 2 has no answer correct flag'}");
									ok = 0;
								}
							}
						}
						if (args.length >= 10 && ok == 1) {
							String[] args9 = args[9].split("'");
							args9[0] = args9[0].replace(" ", "");
							if (!args9[0].equals("-answer-3")) {
								System.out.println("{'status' : 'error', 'message' : " +
										"'Answer 3 has no answer description'}");
								ok = 0;
							}
							if (ok == 1) {
								String[] args10 = args[10].split("'");
								args10[0] = args10[0].replace(" ", "");
								if (!args10[0].equals("-answer-3-is-correct")) {
									System.out.println("{'status' : 'error', 'message' : " +
											"'Answer 3 has no answer correct flag'}");
									ok = 0;
								}
							}
						}
						if (args.length >= 12 && ok == 1) {
							String[] args11 = args[11].split("'");
							args11[0] = args11[0].replace(" ", "");
							if (!args11[0].equals("-answer-3")) {
								System.out.println("{'status' : 'error', 'message' : " +
										"'Answer 4 has no answer description'}");
								ok = 0;
							}
							if (ok == 1) {
								String[] args12 = args[12].split("'");
								args12[0] = args12[0].replace(" ", "");
								if (!args12[0].equals("-answer-4-is-correct")) {
									System.out.println("{'status' : 'error', 'message' : " +
											"'Answer 4 has no answer correct flag'}");
									ok = 0;
								}
							}
						}
						if (args.length >= 14 && ok == 1) {
							String[] args13 = args[13].split("'");
							args13[0] = args13[0].replace(" ", "");
							if (!args13[0].equals("-answer-2")) {
								System.out.println("{'status' : 'error', 'message' : " +
										"'Answer 5 has no answer description'}");
								ok = 0;
							}
							if (ok == 1) {
								String[] args14 = args[14].split("'");
								args14[0] = args14[0].replace(" ", "");
								if (!args14[0].equals("-answer-5-is-correct")) {
									System.out.println("{'status' : 'error', 'message' : " +
											"'Answer 5 has no answer correct flag'}");
									ok = 0;
								}
							}
						}

						if (ok == 1) {
							String[] args4 = args[4].split("'");
							if (questionExist(questions, args4[1]))
								System.out.println("“status” : “error”, “message” : “Question " +
										"already exists”");
							else {
								User userica = new User(args1[1], args2[1]);
								String[] args3 = args[3].split("'");

								Question q = new Question(userica, args3[1], args4[1]);

									String[] args61 = args[6].split(" ");
									q.addanswers(q, args5[1], args61[1]);
								int okish = 0;
									String[] args7 = args[7].split("'");
									String[] args8 = args[8].split(" ");

									if (!q.verifydouble(q.answers, args7[1]))
										q.addanswers(q, args7[1], args8[1]);
									else {
										okish = 1;
										System.out.println("{'status' : 'error', 'message' : " +
												"'Same answer provided more than once'}");
									}

								if (args.length >= 11) {
									String[] args9 = args[9].split("'");
									String[] args10 = args[10].split(" ");

									if (!q.verifydouble(q.answers, args9[1]))
										q.addanswers(q, args9[1], args10[1]);
									else {
										okish = 1;
										System.out.println("{'status' : 'error', 'message' : " +
												"'Same answer provided more than once'}");
									}
								}
								if (args.length >= 13) {
									String[] args11 = args[11].split("'");
									String[] args12 = args[12].split(" ");

									if (!q.verifydouble(q.answers, args11[1]))
										q.addanswers(q, args11[1], args12[1]);
									else {
										okish = 1;
										System.out.println("{'status' : 'error', 'message' : " +
												"'Same answer provided more than once'}");
									}
								}
								if (args.length >= 15) {
									String[] args13 = args[13].split("'");
									String[] args14 = args[14].split(" ");

									if (!q.verifydouble(q.answers, args13[1]))
										q.addanswers(q, args13[1], args14[1]);
									else {
										okish = 1;
										System.out.println("{'status' : 'error', 'message' : " +
												"'Same answer provided more than once'}");
									}
								}
								if (okish == 0 && args4[1].equals("single") && q.numara() > 1) {
									System.out.println("{'status' : 'error', 'message' : " +
											"'Single correct answer question has more " +
											"than one correct answer'}");
									okish = 1;
								}
								if (okish == 0) {
									if (questionExist(questions, args3[1]))
										System.out.println("{'status' : 'error', 'message' : " +
												"'Question already exists'}");
									else {
										int Idquestion = Question.getID();
										Question.setID(Idquestion);
										q.setIDforQuestion(Question.getID());
										try (FileWriter fw = new FileWriter("myfile.csv", true);
											 BufferedWriter bw = new BufferedWriter(fw);
											 PrintWriter out = new PrintWriter(bw)) {
											out.print(args1[1]);
											out.print(",");
											out.print(args2[1]);
											out.print(",");
											out.print(args3[1]);
											out.print(",");
											out.print(args4[1]);
											out.print(",");
											out.print(q.getIDforQuestion());
											for (int i = 0; i < q.answers.size(); i++) {
												out.print(",");
												out.print(q.answers.get(i));
												out.print(",");
												out.print(q.correct.get(i));
											}
											out.println();
											System.out.println("{'status' : 'ok', 'message' : 'Question " +
													"added successfully'}");

										} catch (IOException e) {
											//exception handling left as an exercise for the reader
											e.printStackTrace();
										}
									}
								}
							}
						}
					}
				}
			}
			if (args[0].equals("-get-question-id-by-text")) {
				if (args.length < 2) System.out.println("{'status' : 'error', 'message' " +
						": 'You need to be authenticated'}");

				else if (args.length == 2 || (!args[1].contains("-u") || !args[2].contains("-p"))) {
					System.out.println("{'status' : 'error', 'message' " +
							": 'You need to be authenticated'}");
				} else {
					try {
						BufferedReader br = new BufferedReader(new FileReader((file)));
						String line;
						while ((line = br.readLine()) != null) {
							String[] parols = line.split(",");
							if (parols.length >= 2) {
								User u = new User(parols[0], parols[1]);
								users.add(u);
								if (parols.length > 2) {
									int ID = Integer.parseInt(parols[4]);
									Question q = new Question(u, parols[2], parols[3],ID);
									questions.add(q);
								}
							}
							// process the line
						}
					}catch (IOException e) {
						e.printStackTrace();
					}
					int ok = 1;
					if (args.length == 3) {
						String[] args1 = args[1].split("'");
						String[] args2 = args[2].split("'");
						User u = new User(args1[1], args2[1]);
						if (!userExists(users, u)) {
							System.out.println("{'status' : 'error', 'message' : " +
									"'Login failed'}");
							ok = 0;
						}
					}
					if (ok == 1 && args.length >= 4) {
						String[] args3 = args[3].split("'");
						if (!questionExist(questions, args3[1])) {
							System.out.println("{'status' : 'error', 'message' : " +
									"'Question does not exist'}");
						} else {
							for (Question q : questions) {
								if (q.getQuestion().equals(args3[1])) {
									System.out.println("{'status' : 'ok', 'message' : '" + q.getIDforQuestion() + "'}");
								}
							}
						}
					}

				}
			}
			if (args[0].equals("-get-all-questions")) {
				if (args.length < 2) System.out.println("{'status' : 'error', 'message' " +
						": 'You need to be authenticated'}");

				else if (args.length == 2 || (!args[1].contains("-u") || !args[2].contains("-p"))) {
					System.out.println("{'status' : 'error', 'message' " +
							": 'You need to be authenticated'}");
				} else {
					try {
						BufferedReader br = new BufferedReader(new FileReader((file)));
						String line;
						while ((line = br.readLine()) != null) {
							String[] parols = line.split(",");
							if (parols.length >= 2) {
								User u = new User(parols[0], parols[1]);
								users.add(u);
								if (parols.length > 4) {
									int ID = Integer.parseInt(parols[4]);
									Question q = new Question(u, parols[2], parols[3],ID);
									questions.add(q);
								}
							}
						}
					} catch (IOException e) {
						e.printStackTrace();
					}

					String[] args1 = args[1].split("'");
					String[] args2 = args[2].split("'");
					User u = new User(args1[1], args2[1]);
					if (!userExists(users, u)) {
						System.out.println("{'status' : 'error', 'message' : " +
								"'Login failed'}");

					} else {
						System.out.print("{'status' : 'ok', 'message' : '[");
						int s = 0;
						for (Question q : questions) {
							System.out.print("{\"question_id\" : \"" + q.getIDforQuestion() +
									"\", \"question_name\" : " + "\"" + q.getQuestion() +
									"\"}");
							if (s < questions.size() - 1) System.out.print(", ");
							s++;
						}
						System.out.print("]'}");
					}
				}
			}
			if (args[0].equals("-create-quizz")) {
				if (args.length == 1) System.out.println("{ 'status' : 'error', 'message' : " +
						"'You need to be authenticated'}");
				else if (args.length == 2) {
					System.out.println("{ 'status' : 'error', 'message' : " +
							"'You need to be authenticated'}");
				}
				else {
					try {
						BufferedReader br = new BufferedReader(new FileReader((file)));
						String line;
						while ((line = br.readLine()) != null) {
							String[] parols = line.split(",");
							if (parols.length >= 2) {
								User u = new User(parols[0], parols[1]);
								users.add(u);
								if (!parols[0].equals("quizz") && parols.length > 2) {
									int ID = Integer.parseInt(parols[4]);
									Question q = new Question(u, parols[2], parols[3],ID);
									questions.add(q);
								}
								if(parols[0].equals("quizz")){
									Quizz quiz = new Quizz(parols[1]);
									quizz.add(quiz);
								}
							}
						}
					}catch (IOException e) {
						e.printStackTrace();
					}
					String[] args1 = args[1].split("'");
					String[] args2 = args[2].split("'");
					User u = new User(args1[1], args2[1]);
					if (!userExists(users, u)) {
						System.out.println("{'status' : 'error', 'message' : " +
								"'Login failed'}");
					}
					else {
                      if(args.length > 14)System.out.println("{ 'status' : 'error', " +
							  "'message' : 'Quizz has more than 10 questions'}");
					  else {
                          String[] args3 = args[3].split("'");
						  if(QuizzExist(quizz,args3[1]))
							  System.out.println("{ 'status' : 'error', 'message' : " +
									  "'Quizz name already exists'}");
						  else{
							  int i = 4,contor = 1;
							  while(i < args.length){
								  String[] args4 = args[i].split("'");
								  int id = Integer.parseInt(args4[1]);
								  if(!IDExist(questions,id)){
									  System.out.println("{ 'status' : 'error', 'message' : " +
											  "'Question ID for question "+ id +
											  " does not exist'}");
									  contor = 0;
									  break;
								  }
								  i++;
							  }
							  if(contor == 1){
								  Quizz q = new Quizz(args3[1]);
								  try (FileWriter fw = new FileWriter("myfile.csv", true);
									   BufferedWriter bw = new BufferedWriter(fw);
									   PrintWriter out = new PrintWriter(bw)) {
									  int setID = Quizz.getIDquizz();
									  Quizz.setIDquizz(setID);
									  q.setIDquiz(Quizz.getIDquizz());
									  int s = q.getIDquiz();
									  out.print("quizz,"+args3[1]);
									  out.print(",");
									  out.print(args1[1] + "," + args2[1]);
									  out.print(",");
									  out.print(s + ",");
									  int j = 4;
									  while(j < args.length){
										  String[] args4 = args[j].split("'");
										  out.print(args4[1]);
										  if(j < args.length - 1) out.print(",");
										  j++;
									  }
									  out.println();
									  System.out.print("{'status' : 'ok', 'message' : 'Quizz added succesfully'}");
								  } catch (IOException e) {
									  //exception handling left as an exercise for the reader
									  e.printStackTrace();
								  }
							  }
						  }
					  }
					}
				}
			}
			if(args[0].equals("-get-quizz-by-name")){
                   if(args.length == 1)System.out.println("{ 'status' : 'error', 'message' : " +
						   "'You need to be authenticated'}");
				   else{
					   if(args.length < 3)System.out.println("{ 'status' : 'error', 'message' : " +
							   "'You need to be authenticated'}");
					   else {
						   try {
							   BufferedReader br = new BufferedReader(new FileReader((file)));
							   String line;
							   while ((line = br.readLine()) != null) {
								   String[] parols = line.split(",");
								   if (parols.length >= 2) {
									   User u = new User(parols[0], parols[1]);
									   users.add(u);
									   if(parols[0].equals("quizz")){
										   Quizz quiz = new Quizz(parols[1]);
										   quizz.add(quiz);
									   }
									   else {
										   if (parols.length > 4) {
											   int ID = Integer.parseInt(parols[4]);
											   Question q = new Question(u, parols[2], parols[3], ID);
											   questions.add(q);
										   }
									   }
								   }
							   }
						   }catch (IOException e) {
							   e.printStackTrace();
						   }
						   String[] args1 = args[1].split("'");
						   String[] args2 = args[2].split("'");
						   User u = new User(args1[1],args2[1]);
						   if(!userExists(users,u))System.out.println("{'status' " +
								   ": 'error', 'message' : 'Login failed'}");
						   if(args.length > 3 && userExists(users,u)) {
							   String[] args3 = args[3].split("'");
							   if(!QuizzExist(quizz,args3[1]))
								   System.out.println("{ 'status' : 'error'," +
										   " 'message' : 'Quizz does not exist'}");
							   else {
								   for(Quizz q : quizz)
									   if(q.getName().equals(args3[1]))
										   System.out.println("{ 'status' : 'ok'," +
											   " 'message' : '" + Quizz.getIDquizz()  + "'}");
							   }
						   }
					   }
				   }
			}
			if(args[0].equals("-get-all-quizzes")){
				if(args.length < 3)System.out.println("{ 'status' : 'error', " +
						"'message' : 'You need to be authenticated'}");
				else {
					try {
						BufferedReader br = new BufferedReader(new FileReader((file)));
						String line;
						while ((line = br.readLine()) != null) {
							String[] parols = line.split(",");
							if (parols.length >= 2) {
								User u = new User(parols[0], parols[1]);
								users.add(u);
								if(parols[0].equals("quizz")){
									int ID = Integer.parseInt(parols[4]);
									Quizz quiz = new Quizz(parols[1],ID);
									quizz.add(quiz);
								}
								else {
									if (parols.length > 2) {
										int ID = Integer.parseInt(parols[4]);
										Question q = new Question(u, parols[2], parols[3],ID);
										questions.add(q);
									}
								}

							}
						}
					}catch (IOException e) {
						e.printStackTrace();
					}
					String[] args1 = args[1].split("'");
					String[] args2 = args[2].split("'");
					User u = new User(args1[1], args2[1]);
					if (!userExists(users, u)) {
						System.out.println("{'status' : 'error', 'message' : " +
								"'Login failed'}");
					}
					else {
						System.out.print("{ 'status' : 'ok', 'message' : '[");
						int sum = 0;
						for (Quizz q : quizz) {
							String first = q.isCompleted() ? "True" : "False";
                              System.out.print("{\"quizz_id\" : \"" + q.getIDquiz() + "" +
								"\", \"quizz_name\" : \"" +
								q.getName() + "\", \"is_completed\" : " + "\"" +
									  first + "\"");
							  if(sum < quizz.size()-1)System.out.print("}, ");
							  else System.out.print("}");
							  sum++;
						}
						System.out.println("]'}");
					}
				}
			}
			if(args[0].equals("-get-quizz-details-by-id")){
				if(args.length < 3)System.out.println("{ 'status' : 'error', " +
						"'message' : 'You need to be authenticated'}");
				else {
				      try {
						BufferedReader br = new BufferedReader(new FileReader((file)));
						String line;
						while ((line = br.readLine()) != null) {
							String[] parols = line.split(",");
							if (parols.length >= 2) {
								User u = new User(parols[0], parols[1]);
								users.add(u);
								if (!parols[0].equals("quizz") && parols.length > 2) {
									int ID = Integer.parseInt(parols[4]);
									Question q = new Question(u, parols[2], parols[3], ID);
									int[] sumi = new int[parols.length - 5];
									for (int i = 5; i < parols.length; i += 2) {
										q.addAnswer(parols[i]);
											sumi[i - 5] = indexforAnswer;
											indexforAnswer++;
										q.addCorrect(parols[i + 1]);
									}
									Question.setIDanswer(sumi);
									q.setIDforanswer(Question.getIDanswer());
									questions.add(q);
								}
								if(parols[0].equals("quizz")){
									Quizz quiz = new Quizz(parols[1], Integer.parseInt(parols[4]));
									for(int i = 5;i < parols.length;i++){
										quiz.addQuestion(findQuestionByID(questions,Integer.parseInt(parols[i])));
									}
									quizz.add(quiz);
								}
							}
						}
					}catch (IOException e) {
						e.printStackTrace();
					}
					String[] args1 = args[1].split("'");
					String[] args2 = args[2].split("'");
					User u = new User(args1[1], args2[1]);
					if (!userExists(users, u)) {
						System.out.println("{'status' : 'error', 'message' : " +
								"'Login failed'}");
					}
					else {
						String[] args3 = args[3].split("'");
						int ID = Integer.parseInt(args3[1]);
						if(!quizzExists(quizz, ID))
							System.out.println("{'status' : 'error', 'message' : " +
									"'Quizz does not exist'}");
						else {

							System.out.print("{ 'status' : 'ok', 'message' : '[");
							Quizz qs = findQuizbyId(quizz,ID);
							List<Question> questionList = qs.getQuestions();
							int s = 1;
							int sumisor = 0;
							for(Question que : questionList){
								System.out.print("{\"question-name\":\"" + que.getName() + "" +
										"\", \"question_index\":\"" +
										s + "\", \"question_type\":\""
								+ que.getType() + "\", \"answers\":\"[");
								s++;
								List<String> answers = que.getAnswers();

								int sum = 0;
								for(String ans1 : answers){
									System.out.print("{\"answer_name\":\"" + ans1 + "\"");
									System.out.print(", ");
									System.out.print("\"answer_id\":\"" + que.getIDforanswer()[sum] + "\"}");
									if(sum < questionList.size()-1)System.out.print(", ");
									sum++;
								}
								System.out.print("]\"}");
								if(sumisor < questionList.size() - 1)System.out.print(", ");
								sumisor++;
							}
							System.out.print("]'}");
						}
					}
				}
			}
			if(args[0].equals("-submit-quizz")) {
				if (args.length < 3) System.out.println("{ 'status' : 'error', " +
						"'message' : 'You need to be authenticated'}");
				else {
					int[] x = Question.getIDanswer();
					int nrQuestion = 0,numOfQuestion = 0;
					if(x != null){
						Arrays.fill(x, -1);
						Question.setIDanswer(x);
					}
					try {
						BufferedReader br = new BufferedReader(new FileReader((file)));
						String line;
						while ((line = br.readLine()) != null) {
							String[] parols = line.split(",");
							if(parols[0].equals("QUIZZDONE")){
								String[] args1 = args[1].split("'");
								String[] args2 = args[2].split("'");
								String[] args3 = args[3].split("'");
								if(parols[1].equals(args1[1]) && parols[2].equals(args2[2]) && parols[3].equals(args3[1])){
									System.out.println("{'status' : 'error', 'message' : " +
											"'You have already submitted this quizz'}");
									return;
								}
							}
							if (parols.length >= 2) {
								User u = new User(parols[0], parols[1]);
								users.add(u);
								if(parols[0].equals("quizz")){
									Quizz quiz = new Quizz(parols[1], Integer.parseInt(parols[4]));
									for(int i = 5;i < parols.length;i++){
										nrQuestion++;
									}
									for(int i = 5;i < parols.length;i++){
										Question q = findQuestionByID(questions,Integer.parseInt(parols[i]));
										quiz.addQuestion(q);
									}
									quizz.add(quiz);
								}
								if (!parols[0].equals("quizz") && parols.length > 2) {
									int ID = Integer.parseInt(parols[4]);
									Question q = new Question(u, parols[2], parols[3], ID);
									Set<Answer> ansQuestion = new HashSet<>();
									int counttrue = 0,nrans = 0,k1 = numOfQuestion;
									for(int i = 5;i < parols.length;i += 2) {
										if (parols[i + 1].equals("'1'")) counttrue++;
										nrans++;
									}
									float correct = (float)1 / counttrue;
//									correct = correct / counttrue;
									int wrongnr = nrans - counttrue;
									float wrong = 0 - (float)1 / wrongnr;
//									wrong = 0 - wrong / (nrans - counttrue);
									for (int i = 5; i < parols.length; i += 2) {
										q.addAnswer(parols[i]);
										q.addCorrect(parols[i + 1]);
										int k = Answer.getID();
										k++;
										Answer.setID(k);

										Answer a = new Answer(parols[i],parols[i + 1],k + 1);
										if (parols[i + 1].equals("'1'"))a.points =  correct;
										else a.points = wrong;
										ans.add(a);
										ansQuestion.add(a);
										q.addAnswers(a);
										q.answersss.add(a);
										for(int j = 4;j < args.length;j++){
											String[] args4 = args[j].split("'");
											int IDans = Integer.parseInt(args4[1]);
											if(AnswerExist(ansQuestion,IDans))numOfQuestion = k1 + 1;
										}
									}
									questions.add(q);
								}
							}
						}
					}catch (IOException e) {
						e.printStackTrace();
					}
					String[] args1 = args[1].split("'");
					String[] args2 = args[2].split("'");
					User u = new User(args1[1], args2[1]);
					if (!userExists(users, u)) {
						System.out.println("{'status' : 'error', 'message' : " +
								"'Login failed'}");
					}
					else {
						if(args.length == 3)
							System.out.println("{'status':'error','message':'" +
										"No quizz identifier was provided'}");
						if(args.length > 3) {
							String[] args3 = args[3].split("'");
							int ID = Integer.parseInt(args3[1]);
							if (!quizzExists(quizz,ID))
								System.out.println("{'status':'error'," +
										"'message':'No quiz was found'}");
							else {
                                 Quizz quiz = quizzPosition(quizz,ID);
								 if(quiz.getName().equals(args1[1]))
									 System.out.println("{'status':'ok'," +
											 "'message':'You cannot answer your quizz'");
								 float sums = 0,ok = 1;
								 Set<Question> qs = new HashSet<>();
								 Question q;
								 for(int i = 4;i < args.length;i++){
									 String[] args4 = args[i].split("'");
                                     int IDans = Integer.parseInt(args4[1]);
									 if(!AnswerExist(ans,IDans)){
										 System.out.println("{'status':'error'," +
												 "'message':'Answer ID for answer " + IDans + " does not exist'}");
									    										 ok = 0;
									 }
									 else {
										 Answer a = AnswerPosition(ans,IDans);
										 q = QuestionAnswer(questions,a);
										 q.points += a.points;
										 qs.add(q);
									 }
								 }
								 if(ok == 1){
									 for(Question q1 : qs){
										 q1.points = q1.points / nrQuestion;
										 sums += q1.points;
									 }
									 if(sums <= 0)System.out.println("{'status':'ok'," +
											 "'message':'"+ 0 + " points'}");
									 else System.out.println("{'status':'ok'," +
										 "'message':'"+ Math.round(sums * 100) + " points'}");
									 try (FileWriter fw = new FileWriter("myfile.csv", true);
										  BufferedWriter bw = new BufferedWriter(fw);
										  PrintWriter out = new PrintWriter(bw)) {
										 out.print("QUIZZDONE");
										 out.print(",");
										 out.print(args1[1]);
										 out.print(",");
										 out.print(args2[1]);
										 out.print(",");
										 out.print(args3[1]);
										 Quizz qq = quizzPosition(quizz,ID);
										 out.print(",");
										 out.print(qq.getName());
										 out.print(",");
										 out.print(Math.round(sums * 100));
										 out.print(",");
										 indexforSubmitedQuizz++;
										 out.print(indexforSubmitedQuizz);
										 out.println();



									 } catch (IOException e) {
										 //exception handling left as an exercise for the reader
										 e.printStackTrace();
									 }
								 }
							}
						}
					}
				}
			}
			if(args[0].equals("-delete-quizz-by-id")){
              if(args.length < 3)System.out.println("{ 'status' : 'error', 'message' : " +
					  "'You need to be authenticated'}");
			  else {
				  try{
					  BufferedReader br = new BufferedReader(new FileReader((file)));
					  String line;
					  while ((line = br.readLine()) != null) {
						  String[] parols = line.split(",");
						  if (parols.length >= 2) {
							  User u = new User(parols[0], parols[1]);
							  users.add(u);
							  if (!parols[0].equals("quizz") && parols.length > 2) {
								  int ID = Integer.parseInt(parols[4]);

								  Question q = new Question(u, parols[2], parols[3], ID);
								  for (int i = 5; i < parols.length; i += 2) {
									  q.addAnswer(parols[i]);
									  q.addCorrect(parols[i + 1]);
								  }
								  questions.add(q);
							  }
							  if (parols[0].equals("quizz")) {
								  int ID = Integer.parseInt(parols[4]);
								  Quizz quiz = new Quizz(parols[1],ID);
								  for (int i = 5; i < parols.length; i++) {
									  quiz.addQuestion(findQuestionByID(questions, Integer.parseInt(parols[i])));
								  }
								  quizz.add(quiz);
							  }
						  }
					  }
				  }catch (IOException e) {
					  e.printStackTrace();
				  }
				  String[] args1 = args[1].split("'");
				  String[] args2 = args[2].split("'");
				  User u = new User(args1[1], args2[1]);
				  if (!userExists(users, u)) {
					  System.out.println("{'status' : 'error', 'message' : " +
							  "'Login failed'}");
				  }
				  else {
					  if (args.length == 3) System.out.println("{ 'status' : 'error', 'message' : " +
							  "'No quizz identifier was provided'}");
					  else {
						  String[] args3 = args[3].split("'");
						  int ID = Integer.parseInt(args3[1]);
						  if (!quizzExists(quizz, ID)) System.out.println("{'status' : 'error', 'message' : " +
								  "'No quiz was found'}");
						  else{
							  Quizz q = findQuizbyId(quizz, ID);
							  quizz.remove(q);
							  System.out.println("{'status' : 'ok', 'message' : " +
									  "'Quizz deleted successfully'}");
						  }
					  }
				  }
			}
			}
			if(args[0].equals("-get-my-solutions")) {
				if (args.length < 3) System.out.println("{ 'status' : 'error', 'message' : " +
						"'You need to be authenticated'}");
				else {
					try {
						BufferedReader br = new BufferedReader(new FileReader((file)));
						String line;
						while ((line = br.readLine()) != null) {
							String[] parols = line.split(",");
							if (parols.length >= 2) {
								User u = new User(parols[0], parols[1]);
								users.add(u);

								if (parols[0].equals("QUIZZDONE")) {
									String idquiz = parols[3];
									String nameQuiz = parols[4];
									String score = parols[5];
									String index_complexed = parols[6];
									System.out.println("{ 'status' : 'ok', 'message' : " +
											"'[{\"quiz-id\" : \"" + idquiz + "\", \"quiz-name\" " +
											": \"" + nameQuiz +
											"\", \"score\" : \"" + score +
											"\", \"index_in_list\" : \""+
											index_complexed+"\"}]'}");


								}
							}
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
					String[] args1 = args[1].split("'");
					String[] args2 = args[2].split("'");
					User u = new User(args1[1], args2[1]);
					if (!userExists(users, u)) {
						System.out.println("{'status' : 'error', 'message' : " +
								"'Login failed'}");
					}
                    else {

					}
				}
			}
		}
	}
}

	class User {
		private final String name;
		private final String password;

		User(String name, String password) {
			this.name = name;
			this.password = password;
		}

		public String getName() {
			return name;
		}
		public String getPassword() {
			return password;
		}
	}
	class Answer {
		private final String answer;
		private final String correct;

		private static int ID = 0;
		private final int id;

		public double points;

		Answer(String answer, String correct,int id) {
			this.answer = answer;
			this.correct = correct;
			this.id = id;
		}

		public String getAnswer() {
			return answer;
		}
		public String isCorrect() {
			return correct;
		}
		public static int getID() {
			return ID;
		}
		public int getId() {
			return id;
		}
		public static void setID(int ID) {
			Answer.ID = ID;
		}
	}
	class Question {
		private final User user;
        public float points = 0;
		private int index;
		private int[] IDforanswer;
		private static int[] IDanswer;

		private Answer answer;

		public Set<Answer> answersss = new HashSet<Answer>();

		public Answer addAnswers(Answer answer) {
			if(answer.isCorrect().equals("1"))
				this.answer = new Answer(answer.getAnswer(),"true" , Answer.getID());
			else {
				this.answer = new Answer(answer.getAnswer(),"false" , Answer.getID());
			}
			return this.answer;
		}

		public int[] getIDforanswer() {
			return IDforanswer;
		}

		public void setIDforanswer(int[] IDforanswer) {
			this.IDforanswer = IDforanswer;
		}

		public static int[] getIDanswer() {
			return IDanswer;
		}

		public static void setIDanswer(int[] IDanswer) {
			IDanswer[0] = IDanswer[0] + 1;
			for(int i = 1;i < IDanswer.length;i++) {
				IDanswer[i] = IDanswer[i - 1] + 1;
			}
			Question.IDanswer = IDanswer;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}
        private static int IDquestion = 0;
		private int IDforQuestion;
		private final String question;
		private String type;//simple, multiple
		public List<String> answers = new ArrayList<String>();
		public List<String> correct = new ArrayList<>();
		private static int count = 0;

		public String getQuestion() {
			return question;
		}

		public int getIDforQuestion() {
			return IDforQuestion;
		}

		public void setIDforQuestion(int IDforQuestion) {
			this.IDforQuestion = IDforQuestion;
		}

		public static int getID() {
			return IDquestion;
		}
		public static void setID(int ID) {
			ID++;
			Question.IDquestion = ID;
		}

		public int numara(){
			count = 0;
			for(String s :correct){
				if(s.equals("'1'"))
					count++;
			}
			return count;
		}
        public boolean verifydouble(List<String> answers,String answer){
			return answers.contains(answer);
		}
		Question(User user, String question, String type,int ID) {
			this.user = user;
			this.question = question;
			this.type = type;
			this.IDforQuestion = ID;
		}
		Question(User user, String question,String type) {
			this.user = user;
			this.question = question;
			this.type = type;
		}
		public void addanswers(Question q,String answers,String x){
			q.answers.add(answers);
			q.correct.add(x);
		}
		public String getName() {
			return question;
		}
		public String getType() {
			return type;
		}
		public List<String> getAnswers() {
			return answers;
		}
		public void addAnswer(String answer) {
			answers.add(answer);
		}
		public void addCorrect(String correct) {
			this.correct.add(correct);
		}
	}
	class Quizz{
	    Quizz(String name){
			this.name = name;
	    }
		Quizz(String name,int IDquiz){
			this.name = name;
			this.IDquiz = IDquiz;
		}
        private final String name;
		public List<Question> questions = new ArrayList<>();
		private Boolean isCompleted = false;
		private static int ID = 0;

		private int IDquiz;

		public boolean isCompleted() {
			return isCompleted;
		}


		public int getIDquiz() {
			return IDquiz;
		}

		public List<Question> getQuestions() {
			return questions;
		}

		public void setIDquiz(int IDquiz) {
			this.IDquiz = IDquiz;
		}

		public static int getIDquizz() {
			return ID;
		}
		public static void setIDquizz(int ID) {
			ID++;
			Quizz.ID = ID;
		}
		public String getName() {
			return name;
		}
		public void addQuestion(Question q){
			questions.add(q);
		}
	}
