package com.exampl.grillmaster

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion


// Victoria Lei and Michelle Yun

/** [com.badlogic.gdx.ApplicationListener] implementation shared by all platforms. */
class GrillMaster : ApplicationAdapter(){
    lateinit var batch : SpriteBatch
    lateinit var camera : OrthographicCamera
    lateinit var background : Texture
    lateinit var gameOver : Texture
    lateinit var font : BitmapFont
    var screenHeight = 0f
    var screenWidth = 0f

    var gameState = 0   // 0: start screen
                        // 1: game in progress
                        // 2: game over
    var pattiesNotBurned = 3

    lateinit var pattyStates: Array<Texture>
    var pattyState = 0
    var pattyFail = false
    var pattyFlipped: Boolean = false
    var pattyWidth = 0f
    var pattyHeight = 0f

    var pattyTimer = 0f
    var newPattyTimer = 0f
    var timePassed = 0f
    var waitForPatty = false
    var pattyFlipping = false

    override fun create() {
        super.create()
        batch = SpriteBatch()
        background = Texture("background.png")
        gameOver = Texture("gameover.png")
        screenHeight = Gdx.graphics.height.toFloat()
        screenWidth = Gdx.graphics.width.toFloat()
        camera = OrthographicCamera(screenWidth, screenHeight)
        pattyStates = arrayOf(Texture("raw.png"), Texture("medium.png"),
            Texture("done.png"),Texture("burnt.png"))

        font = BitmapFont()
        font.color = Color.WHITE
        font.data.setScale(10f)

        // patty 1
        pattyWidth = pattyStates[0].width.toFloat()
        pattyHeight = pattyStates[0].height.toFloat()

        initialize()

    } // create

    fun initialize() {
        pattiesNotBurned = 3
        gameState = 0
        pattyState = 0

        // set patty timer
        newPattyTimer = 0f
        timePassed = 0f
        pattyTimer = (1..7).random().toFloat()

    }

    override fun render() {
        super.render()
        batch.begin()

        batch.draw(background, 0f, 0f, screenWidth, screenHeight)

        if (gameState==0) { // start screen
            font!!.draw(batch, "Tap to Start", 130f, 1500f)

            if (Gdx.input.justTouched()) {
                gameState = 1
            }
        }
        else if (gameState==1) { // game is in progress
            if (pattiesNotBurned==0) {
                gameState = 2
            }

            else if (pattyState==3) { // patty is burnt
                pattyFail = true
                pattyFlipped = true

                if (Gdx.input.justTouched() || waitForPatty) {
                    waitForPatty = true
                    placeNewPatty()
                } else {
                    batch.draw(pattyStates[pattyState], 400f, 500f, pattyWidth, pattyHeight)
                }
            }

            else if (Gdx.input.justTouched() || waitForPatty || pattyFlipping) {
                if (!pattyFlipped) {
                    if (pattyState!=2) {
                        pattyFail = true
                    }
                    pattyFlipped = true
                    pattyFlipping = true
                } else if (pattyFlipping) {
                    flipPatty()
                } else {
                    if (pattyState!=2) {
                        pattyFail = true
                    }
                    waitForPatty = true
                    placeNewPatty()
                }
            }

            else {
                timePassed += Gdx.graphics.getDeltaTime()
                // change patty state if applicable
                if (timePassed > pattyTimer) {
                    changePattyState(pattyState+1)
                }
                batch.draw(pattyStates[pattyState], 400f, 500f, pattyWidth, pattyHeight)
            }

            if (!pattyFlipping && !waitForPatty) {
                if (!pattyFlipped && pattyState<2) font!!.draw(batch, "Wait for meat \nto cook!", 100f, 1500f)
                if (!pattyFlipped && pattyState==2) font!!.draw(batch, "Tap to Flip", 100f, 1500f)
                if (pattyFlipped && pattyState<2) font!!.draw(batch, "Wait for meat \nto cook!", 100f, 1500f)
            }
            if (pattyState==3) font!!.draw(batch, "Burnt!", 100f, 1500f)

            font!!.draw(batch, "" + pattiesNotBurned, 100f, 200f)

        }
        else if (gameState==2) { // game is over
            font!!.draw(batch, "Game Over! \nTap to \nplay again.", 100f, 1500f)
            if (Gdx.input.justTouched()) {
                gameState = 1
                initialize()
            }
        }

        batch.end()

    } // render

    fun placeNewPatty() {
        newPattyTimer += Gdx.graphics.getDeltaTime()
        if (newPattyTimer > 1) {
            changePattyState(0)
            pattyFlipped = false
            if (pattyFail) {
                pattiesNotBurned--
                pattyFail = false
            }
            newPattyTimer = 0f
            waitForPatty = false
        }
    }

    fun changePattyState(newPattyState: Int) {
        pattyState = newPattyState
        pattyTimer = (1..7).random().toFloat()
        timePassed = 0f
    }

    fun flipPatty() {
        newPattyTimer += Gdx.graphics.getDeltaTime()
        
        if (newPattyTimer<0.5) {
            batch.draw(pattyStates[pattyState], 400f, 600f, pattyWidth, pattyHeight)
        } else if (newPattyTimer<1) {
            batch.draw(pattyStates[pattyState], 400f, 700f, pattyWidth, pattyHeight)
        } else if (newPattyTimer<1.5) {
            batch.draw(pattyStates[pattyState], 400f, 600f, pattyWidth, pattyHeight)
        } else {
            // the last iteration
            pattyState = 0
            newPattyTimer = 0f
            pattyFlipping = false
            batch.draw(pattyStates[pattyState], 400f, 500f, pattyWidth, pattyHeight)
        }
    }

    override fun dispose() {
        batch.dispose()
    } //dispose()

}//class GrillMaster
