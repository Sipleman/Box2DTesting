package model;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.callbacks.DestructionListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.common.Color3f;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.contacts.Contact;
import org.jbox2d.dynamics.joints.MouseJoint;

import java.util.LinkedList;
import java.util.Queue;

public abstract class CurrentTestBase implements ContactListener {
  private final Queue<QueueItem> inputKeysQueue;
  protected World m_world;
  private Body groundBody;
  private MouseJoint mouseJoint;

  public World getWorld() {
    return m_world;
  }

  private Body bomb;
  private final Vec2 bombSpawnPoint = new Vec2();
  private boolean bombSpawning = false;

  private final Vec2 mouseWorld = new Vec2();
  private int pointCount;
  private int stepCount;

  private MainModel model;
  private DestructionListener destructionListener;

  private final Vec2 p1 = new Vec2();
  private final Vec2 p2 = new Vec2();


//  private final LinkedList<model.QueueItem> inputQueue;

  private String title = null;
  protected int m_textLine;
  private final LinkedList<String> textList = new LinkedList<String>();

  private float cachedCameraScale;
  private final Vec2 cachedCameraPos = new Vec2();
  private boolean hasCachedCamera = false;

//  private JbSerializer serializer;
//  private JbDeserializer deserializer;

  private boolean dialogOnSaveLoadErrors = true;

  private boolean savePending, loadPending, resetPending = false;
  
  public CurrentTestBase() {
    inputKeysQueue = new LinkedList<QueueItem>();
  }
  
  public void init(MainModel mainModel) {
    this.model = mainModel;
    Vec2 gravity = new Vec2(0, -.98f);
    this.m_world = new World(gravity, true);
    
    bomb = null;
    mouseJoint = null;

    BodyDef bodyDef = new BodyDef();
    groundBody = m_world.createBody(bodyDef);
    
    pointCount = 0;
    stepCount = 0;
    bombSpawning = false;
//    m_world.setDestructionListener(destructionListener);
    m_world.setContactListener(this);
    m_world.setDebugDraw(model.getDraw());

    if (hasCachedCamera) {
      setCamera(cachedCameraPos, cachedCameraScale);
    } else {
      setCamera(getDefaultCameraPos(), getDefaultCameraScale());
    }
//    setTitle(getTestName());

    initTest();
  }
  
  public abstract void initTest();

  public synchronized void step() {
//    float hz = settings.getSetting(TestbedSettings.Hz).value;
    float hz = 1f;
    float timeStep = hz > 0f ? 1f / hz : 0;
//    if (settings.singleStep && !settings.pause) {
//      settings.pause = true;
//    }

//    if (settings.pause) {
//      if (settings.singleStep) {
//        settings.singleStep = false;
//      } else {
//        timeStep = 0;
//      }

//      model.getDraw().drawString(5, m_textLine, "****PAUSED****",
//          Color3f.WHITE);
//      m_textLine += 15;
//    }

    int flags = 0;
    model.getDraw();
    flags += 1;
//    flags += settings.getSetting(TestbedSettings.DrawJoints).enabled ? DebugDraw.e_jointBit
//        : 0;
//    flags += settings.getSetting(TestbedSettings.DrawAABBs).enabled ? DebugDraw.e_aabbBit
//        : 0;
//    flags += settings.getSetting(TestbedSettings.DrawPairs).enabled ? DebugDraw.e_pairBit
//        : 0;
//    flags += settings.getSetting(TestbedSettings.DrawCOMs).enabled ? DebugDraw.e_centerOfMassBit
//        : 0;
//    flags += settings.getSetting(TestbedSettings.DrawTree).enabled ? DebugDraw.e_dynamicTreeBit
//        : 0;
    model.getDraw().setFlags(flags);

    pointCount = 0;

    m_world.step(timeStep,
        5,
        7);

    m_world.drawDebugData();

    if (timeStep > 0f) {
      ++stepCount;
    }
    
    if (mouseJoint != null) {
      mouseJoint.getAnchorB(p1);
      Vec2 p2 = mouseJoint.getTarget();

      model.getDraw().drawSegment(p1, p2, Color3f.GREEN);
    }
    if (bombSpawning) {
      model.getDraw().drawSegment(bombSpawnPoint, mouseWorld,
          Color3f.WHITE);
    }
  }
  public void beginContact(Contact contact) {
    
  }

  public void endContact(Contact contact) {

  }

  public void preSolve(Contact contact, Manifold manifold) {

  }

  public void postSolve(Contact contact, ContactImpulse contactImpulse) {

  }
  
  public void setCamera(Vec2 argPos, float scale) {
    model.getDraw().setCamera(argPos.x, argPos.y, scale);
    hasCachedCamera = true;
    cachedCameraScale = scale;
    cachedCameraPos.set(argPos);
  }
  
  public Vec2 getDefaultCameraPos() {
    return new Vec2(0, 10);
  }

  public float getDefaultCameraScale() {
    return 10;
  }

  public void update() {
    //processing input keys
    if (!inputKeysQueue.isEmpty()) {
      synchronized (inputKeysQueue) {
        while (!inputKeysQueue.isEmpty()) {
          QueueItem i = inputKeysQueue.poll();
          switch (i.type) {
            case KeyPressed:
              keyPressed(i.c, i.code);
              break;
            case KeyReleased:
              keyReleased(i.c, i.code);
              break;
//            case MouseDown:
//              mouseDown(i.p);
//              break;
//            case MouseMove:
//              mouseMove(i.p);
//              break;
//            case MouseUp:
//              mouseUp(i.p);
//              break;
//            case ShiftMouseDown:
//              shiftMouseDown(i.p);
//              break;
          }
        }
      }
    }
    step();
  }
  
  protected void keyPressed(char argChar, int argKeyCode) {
    
  }
  protected void keyReleased(char argKeyChar, int argKeyCode) {
  }
}

enum QueueItemType {
  MouseDown, MouseMove, MouseUp, ShiftMouseDown, KeyPressed, KeyReleased
}

class QueueItem {
  public QueueItemType type;
  public Vec2 p;
  public char c;
  public int code;

  public QueueItem(QueueItemType t, Vec2 pt) {
    type = t;
    p = pt;
  }

  public QueueItem(QueueItemType t, char cr, int cd) {
    type = t;
    c = cr;
    code = cd;
  }
  
}