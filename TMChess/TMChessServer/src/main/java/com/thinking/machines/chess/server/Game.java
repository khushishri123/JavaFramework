package com.thinking.machines.chess.server;
import java.util.*;
public class Game implements java.io.Serializable
{
public String id;
public String player1;
public String player2;
public byte activePlayer;
public List<Move> moves;
public byte[][] board;
}