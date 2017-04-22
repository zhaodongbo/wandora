/*
 * WANDORA
 * Knowledge Extraction, Management, and Publishing Application
 * http://wandora.org
 * 
 * Copyright (C) 2004-2016 Wandora Team
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.wandora.application.tools.git;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Repository;
import org.wandora.application.Wandora;
import org.wandora.application.WandoraTool;
import static org.wandora.application.WandoraToolLogger.CLOSE;
import static org.wandora.application.WandoraToolLogger.WAIT;
import org.wandora.application.gui.UIBox;
import org.wandora.application.tools.AbstractWandoraTool;
import org.wandora.topicmap.TopicMap;
import org.wandora.topicmap.TopicMapException;
import org.wandora.topicmap.TopicMapType;
import org.wandora.topicmap.TopicMapTypeManager;
import org.wandora.topicmap.layered.LayerStack;
import org.wandora.topicmap.packageio.DirectoryPackageInput;
import org.wandora.topicmap.packageio.DirectoryPackageOutput;
import org.wandora.topicmap.packageio.PackageInput;
import org.wandora.topicmap.packageio.PackageOutput;
import org.wandora.topicmap.packageio.ZipPackageInput;


/**
 *
 * @author akikivela
 */
public abstract class AbstractGitTool extends AbstractWandoraTool implements WandoraTool {
    
    private static GitSettings gitSettings;
    
    
    
    
    public void saveWandoraProject() throws IOException, TopicMapException {
        Wandora wandora = Wandora.getWandora();
        String fname = wandora.getCurrentProjectFileName();
        if(fname != null) {
            File f = new File(fname);
            log("Saving project to '" + f.getName() + "'.");
            TopicMap tm = wandora.getTopicMap();
            TopicMapType tmtype = TopicMapTypeManager.getType(tm);

            if(f.isDirectory() || f.exists()) {
                PackageOutput out = new DirectoryPackageOutput(f.getAbsolutePath());
                tmtype.packageTopicMap(tm, out, "", getCurrentLogger());  
                out.close();
            }
        }
    }
    
    
    public void reloadWandoraProject() {
        Wandora wandora = Wandora.getWandora();
        String fname = wandora.getCurrentProjectFileName();
        if(fname != null) {
            File f = new File(fname);
            log("Reloading Wandora project '" + f.getPath() + "'.");

            try {
                wandora.getTopicMap().clearTopicMapIndexes();
            }
            catch(Exception e) {
                log(e);
            }
            try {
                PackageInput in = null;
                if(!f.isDirectory()) {
                    in = new ZipPackageInput(f);
                }
                else {
                    in = new DirectoryPackageInput(f);
                }
                TopicMapType type = TopicMapTypeManager.getType(LayerStack.class);
                TopicMap tm = type.unpackageTopicMap(in,"",getCurrentLogger(),wandora);
                in.close();
                if(tm != null) {
                    wandora.setTopicMap((LayerStack)tm);
                    wandora.setCurrentProjectFileName(f.getAbsolutePath());
                }
            }
            catch(Exception e){
                log(e);
            }
        }
    }
    
    
    
    // -------------------------------------------------------------------------
    
    
    
    public Git getGit() {
        String currentProject = null;
        try {
            Wandora wandora = Wandora.getWandora();
            currentProject = wandora.getCurrentProjectFileName();
            if(currentProject == null) return null;
            File currentProjectFile = new File(currentProject);
            if(currentProjectFile.exists() && currentProjectFile.isDirectory()) {
                Git git = Git.open(currentProjectFile);
                return git;
            }
        } 
        catch (IOException ex) {
            log("Can't read git repository in '"+currentProject+"'.");
        }
        return null;
    }
    
    
        
    // -------------------------------------------------------------------------
    
    
    public GitSettings getGitSettings() {
        if(gitSettings == null) {
            gitSettings = new GitSettings();
        }
        return gitSettings;
    }
    
    
    
    
    public void setGitSettings(GitSettings gitSettings) {
        this.gitSettings = gitSettings;
    }
    
    
    
    public void setGitSettings(String remotePath, String localPath, String username, String password) {
        gitSettings = new GitSettings(remotePath, localPath, username, password);
    }
    
    
    
    
    public void clearCurrentGitSettings() {
        gitSettings = null;
    }
    
    
    
    // -------------------------------------------------------------------------
    
    
    
    @Override
    public Icon getIcon() {
        return UIBox.getIcon(0xf1d3);
    }
}
