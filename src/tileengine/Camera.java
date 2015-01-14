package tileengine;

public class Camera {
        
    
        public void update(){
            followPlayer();
        }
        
        
    
        private void followPlayer(){
            GameHandler.cameraX = GameHandler.playerX - GameHandler.scaleXRes / 2 + GameHandler.spriteSheetScale;
            GameHandler.cameraY = GameHandler.playerY - GameHandler.scaleYRes / 2 + GameHandler.spriteSheetScale;
                        
            //System.out.println("Kamera liikkuu");
        }
        
        private void returnCamera(){
            //System.out.println("Kamera");
            //System.out.println(GameHandler.cameraX);
            //System.out.println(GameHandler.cameraY);
        }
}
